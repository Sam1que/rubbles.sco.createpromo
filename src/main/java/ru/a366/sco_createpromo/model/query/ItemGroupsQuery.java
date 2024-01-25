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
public class ItemGroupsQuery implements Query {
    @JsonProperty("itm_gr_id")
    private Long itmGrId;

    @JsonProperty("itm_cd")
    private Long itmCd;

    @JsonProperty("itm_gr_id_ind1")
    private Long itmGrIdInd1;

    @JsonProperty("itm_gr_id_ind2")
    private Long itmGrIdInd2;

    @JsonProperty("itm_gr_id_prize")
    private Long itmGrIdPrize;

    @JsonProperty("itm_gr_type")
    private String itmGrType;

    @JsonProperty("itm_gr_name")
    private String itmGrName;

    @JsonProperty("check_itm_gr_id")
    private Long checkItmGrId;

    @JsonProperty("promo_id")
    private Long promoId;

    @JsonProperty("itm_desc")
    private String itmDesc;

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
}
