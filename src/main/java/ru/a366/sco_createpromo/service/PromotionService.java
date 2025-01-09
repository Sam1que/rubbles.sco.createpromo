package ru.a366.sco_createpromo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import ru.a366.sco_createpromo.db.DbAdapter;
import ru.a366.sco_createpromo.exception.ErrorHandler;
import ru.a366.sco_createpromo.exception.RubblesDataException;
import ru.a366.sco_createpromo.mapper.PromotionMapper;
import ru.a366.sco_createpromo.model.RubblesRequest;
import ru.a366.sco_createpromo.model.RubblesResponse;
import ru.a366.sco_createpromo.model.ScoRequest;
import ru.a366.sco_createpromo.model.ScoResponse;
import rubbles.asap.sendKafka.adapter.AsapSendKafka;
import rubbles.asap.sendKafka.config.AsapSendKafkaConfig;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Import(AsapSendKafkaConfig.class)
public class PromotionService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AsapSendKafka asapSendKafka;

    @Autowired
    private DbAdapter dbAdapter;

    @Value("${sco.retry_count}")
    private int RETRY_COUNT;

    @Value("${sco.retry_timeout_sec}")
    private int RETRY_TIMEOUT_SEC;

    @Value("${mapping.hostname}")
    private String hostname;

    @Value("${mapping.sco-post}")
    private String scoPost;

    @Value("${sco.header}")
    private String scoHeader;

    @Value("${kafka.recipients}")
    private List<String> asapRecipients;
    @Value("${kafka.sender}")
    private String asapSender;

    public ResponseEntity<?> callScoFromRubbles(RubblesRequest request) throws InterruptedException, RubblesDataException {
        String url = hostname + scoPost;
        int retryNum = 1;
        ScoResponse response = null;
        if(request.getDateStartSrInput() != null) {
            request.setDateStartSr(LocalDate.from(request.getDateStartSrInput()));
        }
        if(request.getDateEndSrInput() != null) {
            request.setDateEndSr(LocalDate.from(request.getDateEndSrInput()));
        }
        ScoRequest scoRequest= null;
        try {
            scoRequest = PromotionMapper.fromRubblesRequest(request);
            log.info("Request to SCO {}", objectMapper.writeValueAsString(scoRequest));

        } catch (Exception e) {
            log.error("Error parsing message from Rubbles: {}", e.getMessage(), e);
            throw new RubblesDataException(e.getMessage());
        }


        do {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.set("X-Auth-Key", scoHeader);

                HttpEntity<ScoRequest> requestEntity = new HttpEntity<>(scoRequest, headers);
                response = restTemplate.postForObject(url, requestEntity, ScoResponse.class);
                log.info("Answer from SCO {}", objectMapper.writeValueAsString(response));
                if (response != null) {
                    if(response.getStatus().equals("ok")) {
                        processResponse(request, response);
                        return ResponseEntity.ok()
                                .body(new RubblesResponse(
                                        response.getPromoId(),
                                        request.getPromoName(),
                                        response.getPlatformPromoId()
                                ));
                    }
                    else {
                        asapSendKafka.send("SCO_CREATE_PROMO", "SmartCheckOut Promt", asapSender, asapRecipients,
                                "Ошибка при создании акции SCO",
                                "<p>Ошибка от SCO при создании акции '" + request.getPromoName() + "': " + response.getErrorText() + "</p>");
                        return ResponseEntity.badRequest()
                                .body(ErrorHandler.ApiError.builder()
                                        .code("error")
                                        .desc("Error from SCO: "+ response.getErrorText())
                                        .build());
                    }
                }
            } catch (Exception e) {
                if (e instanceof HttpClientErrorException && ((HttpClientErrorException) e).getStatusCode().is4xxClientError()) {
                    log.error("Error from SCO: HTTP code={} {}, body={} " ,((HttpClientErrorException) e).getStatusCode(), ((HttpClientErrorException) e).getStatusText(), ((HttpClientErrorException) e).getResponseBodyAsString());
                    ScoResponse scoResponse = null;
                    try {
                        scoResponse = objectMapper.readValue(((HttpClientErrorException) e).getResponseBodyAsString(), ScoResponse.class);
                    } catch (JsonProcessingException ex) {
                        asapSendKafka.send("SCO_CREATE_PROMO", "SmartCheckOut Promt", asapSender, asapRecipients,
                                "Ошибка при создании акции SCO",
                                "<p>Ошибка от SCO при создании акции '" + request.getPromoName() + "': получен некорректный ответ от SCO</p>");
                        throw new RuntimeException(ex);
                    }
                    return ResponseEntity.badRequest()
                            .body(ErrorHandler.ApiError.builder()
                                    .code("error")
                                    .desc("Error from SCO: "+ scoResponse.getErrorText())
                                    .build());
                } else if (e instanceof HttpClientErrorException && ((HttpClientErrorException) e).getStatusCode().is5xxServerError()) {
                    asapSendKafka.send("SCO_CREATE_PROMO", "SmartCheckOut Promt", asapSender, asapRecipients,
                            "Ошибка при создании акции SCO",
                            "<p>Ошибка от SCO при создании акции '" + request.getPromoName() + "': " + response.getErrorText() + "</p>");

                    log.error("Attempt " + retryNum + ": Could not connect to url: " +
                            url + "\nError: " + e.getMessage(), e);
                    if (retryNum < RETRY_COUNT) {
                        Thread.sleep(RETRY_TIMEOUT_SEC * 1000L);
                    }
                    retryNum++;
                } else {
                    log.error("Attempt " + retryNum + ": Could not connect to url: " +
                            url + "\nError: " + e.getMessage(), e);
                    if (retryNum < RETRY_COUNT) {
                        Thread.sleep(RETRY_TIMEOUT_SEC * 1000L);
                    }
                    retryNum++;
                }
            }
        } while (retryNum <= RETRY_COUNT);

        if (response == null) {
            asapSendKafka.send("SCO_CREATE_PROMO", "SmartCheckOut Promt", asapSender, asapRecipients,
                    "Ошибка при создании акции SCO",
                    "<p>Ошибка от SCO при создании акции '" + request.getPromoName() + "': не удалось получить корректный ответ от SCO.</p>");
            throw new RuntimeException("HTTP error was received from SmartCheckout");
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void processResponse(RubblesRequest request, ScoResponse response) {
        new Thread(() -> {
            try {
                log.info("Starting to insert data into the database");
                dbAdapter.processQueries(request, response);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }).start();
    }
}
