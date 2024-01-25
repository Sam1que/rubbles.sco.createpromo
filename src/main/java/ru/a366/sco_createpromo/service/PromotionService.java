package ru.a366.sco_createpromo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import ru.a366.sco_createpromo.db.DbAdapter;
import ru.a366.sco_createpromo.exception.RubblesDataException;
import ru.a366.sco_createpromo.mapper.PromotionMapper;
import ru.a366.sco_createpromo.model.RubblesRequest;
import ru.a366.sco_createpromo.model.RubblesResponse;
import ru.a366.sco_createpromo.model.ScoResponse;

@Service
@Slf4j
public class PromotionService {

    @Autowired
    private RestTemplate restTemplate;

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
                response = restTemplate.postForObject(url,
                        PromotionMapper.fromRubblesRequest(request), ScoResponse.class);
                if (response != null) {
                    if(response.getStatus().equals("success")) {
                        processResponse(request, response);
                        return ResponseEntity.ok()
                                .body(new RubblesResponse(
                                        response.getPromoId(),
                                        request.getPromoName()
                                ));
                    } else {
                        throw new RubblesDataException(response.getErrorText());
                    }
                }
                break;
            } catch (Exception e) {
                log.error("Attempt " + retryNum + ": Could not connect to url: " +
                        url + "\nError: " + e.getMessage(), e);
                if (retryNum < RETRY_COUNT) {
                    Thread.sleep(RETRY_TIMEOUT_SEC * 1000L);
                }
                retryNum++;
            }
        } while (retryNum <= RETRY_COUNT);

        if (response == null) {
            throw new RuntimeException("Error connecting to SCO");
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void processResponse(RubblesRequest request, ScoResponse response) {
        new Thread(() -> {
            try {
                dbAdapter.processQueries(request, response);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }).start();
    }
}
