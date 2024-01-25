package ru.a366.sco_createpromo.model.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class RbPromoAcrmQuery implements Query {
    @JsonProperty("promo_id")
    @NotBlank
    private Long promoId;

    @JsonProperty("promo_name")
    @NotBlank
    private String promoName;

    @JsonProperty("sco_template_num")
    @NotNull
    private Long scoTemplateNum;

    @JsonProperty("sco_template_cd")
    @NotBlank
    private String scoTemplateCd;

    @JsonProperty("about_promo")
    @NotBlank
    private String aboutPromo;

    @JsonProperty("discount_counter")
    @NotNull
    private Long discountCounter;

    @JsonProperty("discount_owner")
    @NotNull
    private String discountOwner;

    @JsonProperty("promo_explation")
    private String promoExplation;

    @JsonProperty("sum_online_order_flg")
    private Long sumOnlineOrderFlg;

    @JsonProperty("dis_online_order_flg")
    @NotNull
    private Long disOnlineOrderFlg;

    @JsonProperty("sum_online_reserve_flg")
    private Long sumOnlineReserveFlg;

    @JsonProperty("dis_online_reserve_flg")
    @NotNull
    private Long disOnlineReserveFlg;

    @JsonProperty("sum_online_ved_flg")
    private Long sumOnlineVedFlg;

    @JsonProperty("dis_online_ved_flg")
    private Long disOnlineVedFlg;

    @JsonProperty("old_dis_in_flg")
    private Long oldDisInFlg;

    @JsonProperty("fract_pac_flg")
    private Long fractPacFlg;

    @JsonProperty("discount_option")
    private String discountOption;

    @JsonProperty("min_item_count")
    private Long minItemCount;

    @JsonProperty("item_count")
    private Long itemCount;

    @JsonProperty("item_ind_count")
    private Long itemIndCount;

    @JsonProperty("item_count_ind2")
    private Long itemCountInd2;

    @JsonProperty("ind_option")
    private String indOption;

    @JsonProperty("discount_type")
    private String discountType;

    @JsonProperty("discount_value")
    private Long discountValue;

    @JsonProperty("prize_count")
    private Long prizeCount;

    @JsonProperty("prize_max_count")
    private Long prizeMaxCount;

    @JsonProperty("promo_online_order_flg")
    private Long promoOnlineOrderFlg;

    @JsonProperty("promo_online_reserve_flg")
    private Long promoOnlineReserveFlg;

    @JsonProperty("srn_flg")
    private Long srnFlg;

    @JsonProperty("report_flg")
    @NotNull
    private Long reportFlg;

    @JsonProperty("date_start_sr")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateStartSr;

    @JsonProperty("date_end_sr")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateEndSr;

    @JsonProperty("start_date")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonProperty("end_date")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @JsonProperty("start_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime startTime;

    @JsonProperty("end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime endTime;

    @JsonProperty("weakday_action")
    private String weakdayAction;

    @JsonProperty("discount_type_1")
    private String discountType1;

    @JsonProperty("discount_value_1")
    private Long discountValue1;

    @JsonProperty("item_count_1")
    private Long itemCount1;

    @JsonProperty("sco_and_or_1")
    private String scoAndOr1;

    @JsonProperty("min_sum_1")
    private Long minSum1;

    @JsonProperty("discount_type_2")
    private String discountType2;

    @JsonProperty("discount_value_2")
    private Long discountValue2;

    @JsonProperty("item_count_2")
    private Long itemCount2;

    @JsonProperty("sco_and_or_2")
    private String scoAndOr2;

    @JsonProperty("min_sum_2")
    private Long minSum2;

    @JsonProperty("discount_type_3")
    private String discountType3;

    @JsonProperty("discount_value_3")
    private Long discountValue3;

    @JsonProperty("item_count_3")
    private Long itemCount3;

    @JsonProperty("sco_and_or_3")
    private String scoAndOr3;

    @JsonProperty("min_sum_3")
    private Long minSum3;

    @JsonProperty("sco_upload_flg")
    private int scoUploadFlg;
}
