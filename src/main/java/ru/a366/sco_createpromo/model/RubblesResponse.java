package ru.a366.sco_createpromo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RubblesResponse {
    @JsonProperty("promotionId")
    private Long promotionId;

    @JsonProperty("promotionName")
    private String promotionName;
}
