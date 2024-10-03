package ru.a366.sco_createpromo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.a366.sco_createpromo.model.RubblesRequest;
import ru.a366.sco_createpromo.service.PromotionService;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class CreatePromoController {

    @Autowired
    private final PromotionService promotionService;

    @Value("${mapping.rubbles-post}")
    private String rubblesPostMapping;

    @Value("${mapping.sco-post}")
    private String scoPostMapping;

    @PostMapping("${mapping.rubbles-post}")
    @ResponseBody
    public ResponseEntity<?> callCftFromRubbles(
            @RequestBody @Valid RubblesRequest rubblesRequest) throws Exception {
        log.info("Received POST {} request", rubblesPostMapping);
        return promotionService.callScoFromRubbles(rubblesRequest);
    }

//    @PostMapping("${mapping.sco-post}")
//    @ResponseBody
//    public ResponseEntity<?> fakeScoMethod(@RequestBody ScoRequest request) {
//        log.info("Received POST {} request", scoPostMapping);
//        ThreadLocalRandom random = ThreadLocalRandom.current();
//        ScoResponse response = new ScoResponse();
//        response.setPromoId(random.nextLong(100L) + 1L);
//        response.setStatus("success");
//        response.setPlatformPromoId(random.nextLong(100L) + 2L);
//        return ResponseEntity.ok(response);
//    }
}
