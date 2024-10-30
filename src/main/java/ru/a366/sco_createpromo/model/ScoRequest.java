package ru.a366.sco_createpromo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ScoRequest {

    //@JsonProperty("prompt_flg")
    //@NotNull
    //private Boolean promptFlg;

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
    @NotBlank
    private String discountOwner;

    @JsonProperty("promo_explation")
    private String promoExplation;

    @JsonProperty("sum_online_order_flg")
    private Boolean sumOnlineOrderFlg;

    @JsonProperty("dis_online_order_flg")
    private Boolean disOnlineOrderFlg;

    @JsonProperty("sum_online_reserve_flg")
    private Boolean sumOnlineReserveFlg;

    @JsonProperty("dis_online_reserve_flg")
    private Boolean disOnlineReserveFlg;

    @JsonProperty("sum_online_ved_flg")
    private Boolean sumOnlineVedFlg;

    @JsonProperty("dis_online_ved_flg")
    private Boolean disOnlineVedFlg;

    @JsonProperty("old_dis_in_flg")
    private Boolean oldDisInFlg;

    @JsonProperty("fract_pac_flg")
    private Boolean fractPacFlg;

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
    private Boolean promoOnlineOrderFlg;

    @JsonProperty("promo_online_reserve_flg")
    private Boolean promoOnlineReserveFlg;

    @JsonProperty("srn")
    private SRN srn;

    @JsonProperty("time_opt")
    private TimeOption timeOpt;

    @JsonProperty("promo_disc1")
    private PromoDiscountOne promoDisc1;

    @JsonProperty("promo_disc2")
    private PromoDiscountTwo promoDisc2;

    @JsonProperty("promo_disc3")
    private PromoDiscountThree promoDisc3;

    @Getter
    @Setter
    public static class PromoDiscountOne {
        @JsonProperty("min_sum_1")
        private Long minSum;

        @JsonProperty("item_count_1")
        private Long itemCount;

        @JsonProperty("sco_and_or_1")
        private String scoAndOr;

        @JsonProperty("discount_type_1")
        private String discountType;

        @JsonProperty("discount_value_1")
        private Long discountValue;
    }

    @Getter
    @Setter
    public static class PromoDiscountTwo {
        @JsonProperty("min_sum_2")
        private Long minSum;

        @JsonProperty("item_count_2")
        private Long itemCount;

        @JsonProperty("sco_and_or_2")
        private String scoAndOr;

        @JsonProperty("discount_type_2")
        private String discountType;

        @JsonProperty("discount_value_2")
        private Long discountValue;
    }

    @Getter
    @Setter
    public static class PromoDiscountThree {
        @JsonProperty("min_sum_3")
        private Long minSum;

        @JsonProperty("item_count_3")
        private Long itemCount;

        @JsonProperty("sco_and_or_3")
        private String scoAndOr;

        @JsonProperty("discount_type_3")
        private String discountType;

        @JsonProperty("discount_value_3")
        private Long discountValue;
    }

    @Getter
    @Setter
    public static class TimeOption {

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        @JsonProperty("start_date")
        @NotNull
        private OffsetDateTime startDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        @JsonProperty("end_date")
        @NotNull
        private OffsetDateTime endDate;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        @JsonProperty("start_time")
        private LocalTime startTime;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        @JsonProperty("end_time")
        private LocalTime endTime;

        @JsonProperty("weekday_action")
        private List<String> weekdayAction;

    }

    @Getter
    @Setter
    public static class SRN {

        @JsonProperty("srn_flg")
        private Long srnFlg;

        @JsonProperty("report_flg")
        @NotNull
        private Long reportFlg;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @JsonProperty("date_start_sr")
        private LocalDate dateStartSr;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @JsonProperty("date_end_sr")
        private LocalDate dateEndSr;
    }

    @JsonProperty("array_itm_ind1")
    private List<Long> arrayItmInd1;

    @JsonProperty("array_itm_ind2")
    private List<Long> arrayItmInd2;

    @JsonProperty("array_itm_prize")
    private List<Long> arrayItmPrize;

    @JsonProperty("array_pos")
    private List<String> arrayPos;

    @JsonProperty("platforms")
    private String platforms;
}
