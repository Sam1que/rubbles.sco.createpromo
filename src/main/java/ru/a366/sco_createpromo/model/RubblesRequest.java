package ru.a366.sco_createpromo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Validated
public class RubblesRequest {

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
    private Long discountCounter;

    @JsonProperty("start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mmXXX")
    private ZonedDateTime startDate;

    @JsonProperty("end_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mmXXX")
    private ZonedDateTime  endDate;

    @JsonProperty("start_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @JsonProperty("end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    @JsonProperty("days_until_start")
    private Long daysUntilStart;

    @JsonProperty("days_until_end")
    private Long daysUntilEnd;

    @JsonProperty("weekday_action")
    private String weekdayAction;

    @JsonProperty("discount_owner")
    private String discountOwner;

    @JsonProperty("promo_explation")
    private String promoExplation;

    @JsonProperty("srn_flg")
    private Boolean srnFlg;

    @JsonProperty("date_start_sr")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mmXXX")
    private ZonedDateTime dateStartSrInput;

    @JsonIgnore
    @Setter
    private LocalDate dateStartSr;

    @JsonProperty("date_end_sr")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mmXXX")
    private ZonedDateTime dateEndSrInput;

    @JsonIgnore
    @Setter
    private LocalDate dateEndSr;

    @JsonProperty("dis_online_order_flg")
    private Boolean disOnlineOrderFlg;

    @JsonProperty("dis_online_reserve_flg")
    private Boolean disOnlineReserveFlg;

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

    @JsonProperty("sum_online_order_flg")
    private Boolean sumOnlineOrderFlg;

    @JsonProperty("sum_online_reserve_flg")
    private Boolean sumOnlineReserveFlg;

    @JsonProperty("sum_online_ved_flg")
    private Boolean sumOnlineVedFlg;

    @JsonProperty("dis_online_ved_flg")
    private Boolean disOnlineVedFlg;

    @JsonProperty("old_dis_in_flg")
    private Boolean oldDisInFlg;

    @JsonProperty("fract_pac_flg")
    private Boolean fractPacFlg;

    @JsonProperty("promo_online_order_flg")
    private Boolean promoOnlineOrderFlg;

    @JsonProperty("promo_online_reserve_flg")
    private Boolean promoOnlineReserveFlg;

    @JsonProperty("min_sum_1")
    private Long minSum1;

    @JsonProperty("min_sum_2")
    private Long minSum2;

    @JsonProperty("min_sum_3")
    private Long minSum3;

    @JsonProperty("discount_type_1")
    private String discountType1;

    @JsonProperty("discount_type_2")
    private String discountType2;

    @JsonProperty("discount_type_3")
    private String discountType3;

    @JsonProperty("discount_value_1")
    private Long discountValue1;

    @JsonProperty("discount_value_2")
    private Long discountValue2;

    @JsonProperty("discount_value_3")
    private Long discountValue3;

    @JsonProperty("sco_and_or_1")
    private String scoAndOr1;

    @JsonProperty("sco_and_or_2")
    private String scoAndOr2;

    @JsonProperty("sco_and_or_3")
    private String scoAndOr3;

    @JsonProperty("item_count_1")
    private Long itemCount1;

    @JsonProperty("item_count_2")
    private Long itemCount2;

    @JsonProperty("item_count_3")
    private Long itemCount3;

    @JsonProperty("array_itm_ind1")
    private List<Long> arrayItmInd1;

    @JsonProperty("array_itm_ind2")
    private List<Long> arrayItmInd2;

    @JsonProperty("array_itm_prize")
    private List<Long> arrayItmPrize;

    @JsonProperty("array_itm_exp")
    private List<Long> arrayItmExp;

    @JsonProperty("array_pos")
    private List<String> arrayPos;

    @JsonProperty("platforms")
    private String platforms;

    @JsonProperty("site")
    private List<Long> site;
}
