package ru.a366.sco_createpromo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ScoResponse {
    @JsonProperty("promo_id")
    private Long promoId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("error_code")
    private Long errorCode;

    @JsonProperty("error_text")
    private String errorText;

    @JsonProperty("platform_promo_id")
    private Long platformPromoId;
}
