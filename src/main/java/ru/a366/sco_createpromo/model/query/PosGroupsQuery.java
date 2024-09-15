package ru.a366.sco_createpromo.model.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PosGroupsQuery implements Query {
    @JsonProperty("pos_gr_id")
    private Long posGrId;

    @JsonProperty("pos_cd")
    private Long posId;

    @JsonProperty("promo_id")
    private Long promoId;

    @JsonProperty("promo_cd")
    private Long promoCd;

    @JsonProperty("int_query_id")
    private Long intQueryId;

    @JsonProperty("int_create_dttm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime intCreateDttm;

    @JsonProperty("int_update_dttm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime intUpdateDttm;

    @JsonProperty("int_status")
    private String intStatus;

    @JsonProperty("int_error_code")
    private int intErrorCode;

    @JsonProperty("int_error_text")
    private String intErrorText;

    @JsonProperty("acrm_check_flg")
    private int acrmCheckFlg;

    @JsonProperty("upload_sco_flg")
    private int uploadScoFlg;
}
