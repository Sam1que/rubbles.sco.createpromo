package ru.a366.sco_createpromo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Service
@Slf4j
public class PromotionService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

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

    public ResponseEntity<?> callScoFromRubbles(RubblesRequest request) throws InterruptedException {
        String url = hostname + scoPost;
        int retryNum = 1;
        ScoResponse response = null;

        do {
            try {
                ScoRequest scoRequest =  PromotionMapper.fromRubblesRequest(request);
                log.debug("Response to SCO {}", objectMapper.writeValueAsString(scoRequest));
                response = restTemplate.postForObject(url, scoRequest, ScoResponse.class);
                log.debug("Answer from SCO {}", objectMapper.writeValueAsString(response));
                if (response != null) {
                    if(response.getStatus().equals("success")) {
                        processResponse(request, response);
                        return ResponseEntity.ok()
                                .body(new RubblesResponse(
                                        response.getPromoId(),
                                        request.getPromoName(),
                                        response.getPlatformPromoId()
                                ));
                    }  else {
                        throw new RubblesDataException(response.getErrorText());
                    }
                }
            } catch (Exception e) {
                if (e instanceof HttpClientErrorException && ((HttpClientErrorException) e).getStatusCode().is4xxClientError()) {
                    log.error("Error from CFT: HTTPcode={} {}, body={} " ,((HttpClientErrorException) e).getStatusCode(), ((HttpClientErrorException) e).getStatusText(), ((HttpClientErrorException) e).getResponseBodyAsString());
                    ScoResponse scoResponse = null;
                    try {
                        scoResponse = objectMapper.readValue(((HttpClientErrorException) e).getResponseBodyAsString(), ScoResponse.class);
                    } catch (JsonProcessingException ex) {
                        throw new RuntimeException(ex);
                    }
                    return ResponseEntity.badRequest()
                            .body(ErrorHandler.ApiError.builder()
                                    .code("error")
                                    .desc("Error from SCO: "+ scoResponse.getErrorText())
                                    .build());
                }
                else {
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
