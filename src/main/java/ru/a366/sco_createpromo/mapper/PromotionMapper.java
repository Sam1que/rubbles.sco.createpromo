package ru.a366.sco_createpromo.mapper;

import ru.a366.sco_createpromo.model.RubblesRequest;
import ru.a366.sco_createpromo.model.ScoRequest;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Arrays;


public class PromotionMapper {
    public static ScoRequest fromRubblesRequest(RubblesRequest rubblesRequest) {
        ScoRequest scoRequest = new ScoRequest();
        //scoRequest.setPromptFlg(rubblesRequest.getPromptFlg());
        scoRequest.setPromoName(rubblesRequest.getPromoName());
        scoRequest.setScoTemplateNum(rubblesRequest.getScoTemplateNum());
        scoRequest.setScoTemplateCd(rubblesRequest.getScoTemplateCd());
        scoRequest.setAboutPromo(rubblesRequest.getAboutPromo());
        scoRequest.setDiscountCounter(rubblesRequest.getDiscountCounter());
        scoRequest.setDiscountOwner(rubblesRequest.getDiscountOwner() != null ? reformatDiscountOwner(rubblesRequest.getDiscountOwner()): null);
        scoRequest.setPromoExplation(rubblesRequest.getPromoExplation() != null ? rubblesRequest.getPromoExplation() : null);
        scoRequest.setSumOnlineOrderFlg(rubblesRequest.getSumOnlineOrderFlg() != null ? rubblesRequest.getSumOnlineOrderFlg() : null);
        scoRequest.setDisOnlineOrderFlg(rubblesRequest.getDisOnlineOrderFlg() != null ? rubblesRequest.getDisOnlineOrderFlg() : null);
        scoRequest.setSumOnlineReserveFlg(rubblesRequest.getSumOnlineReserveFlg() != null ? rubblesRequest.getSumOnlineReserveFlg() : null);
        scoRequest.setDisOnlineReserveFlg(rubblesRequest.getDisOnlineReserveFlg() != null ? rubblesRequest.getDisOnlineReserveFlg() : null);
        scoRequest.setSumOnlineVedFlg(rubblesRequest.getSumOnlineVedFlg() != null ? rubblesRequest.getSumOnlineVedFlg() : null);
        scoRequest.setDisOnlineVedFlg(rubblesRequest.getDisOnlineVedFlg() != null ? rubblesRequest.getDisOnlineVedFlg() : null);
        scoRequest.setOldDisInFlg(rubblesRequest.getOldDisInFlg() != null ? rubblesRequest.getOldDisInFlg() : null);
        scoRequest.setFractPacFlg(rubblesRequest.getFractPacFlg() != null ? rubblesRequest.getFractPacFlg() : null);
        scoRequest.setDiscountOption(rubblesRequest.getDiscountOption() != null ? rubblesRequest.getDiscountOption().toString() : null);
        scoRequest.setMinItemCount(rubblesRequest.getMinItemCount() != null ? rubblesRequest.getMinItemCount() : null);
        scoRequest.setItemCount(rubblesRequest.getItemCount() != null ? rubblesRequest.getItemCount() : null);
        scoRequest.setItemIndCount(rubblesRequest.getItemIndCount() != null ? rubblesRequest.getItemIndCount() : null);
        scoRequest.setItemCountInd2(rubblesRequest.getItemCountInd2() != null ? rubblesRequest.getItemCountInd2() : null);
        scoRequest.setIndOption(rubblesRequest.getIndOption() != null ? rubblesRequest.getIndOption() : null);
        scoRequest.setDiscountType(rubblesRequest.getDiscountType() != null ? reformatDiscountType(rubblesRequest.getDiscountType()) : null);
        scoRequest.setDiscountValue(rubblesRequest.getDiscountValue() != null ? rubblesRequest.getDiscountValue() : null);
        scoRequest.setPrizeCount(rubblesRequest.getPrizeCount() != null ? rubblesRequest.getPrizeCount() : null);
        scoRequest.setPrizeMaxCount(rubblesRequest.getPrizeMaxCount() != null ? rubblesRequest.getPrizeMaxCount() : null);
        scoRequest.setPromoOnlineOrderFlg(rubblesRequest.getPromoOnlineOrderFlg() != null ? rubblesRequest.getPromoOnlineOrderFlg() : null);
        scoRequest.setPromoOnlineReserveFlg(rubblesRequest.getPromoOnlineReserveFlg() != null ? rubblesRequest.getPromoOnlineReserveFlg() : null);
        scoRequest.setSrn(createSrn(rubblesRequest));
        scoRequest.setTimeOpt(createTimeOption(rubblesRequest));
        scoRequest.setPromoDisc1(createPromoDiscountOne(rubblesRequest));
        scoRequest.setPromoDisc2(createPromoDiscountTwo(rubblesRequest));
        scoRequest.setPromoDisc3(createPromoDiscountThree(rubblesRequest));
        return scoRequest;
    }
    private static String reformatDiscountType (String discountType) {
        return discountType.equalsIgnoreCase("Руб")?"RUB":"PERCENT";
    }
    private static String reformatDiscountOwner (String discountOwner) {
        if (discountOwner.equalsIgnoreCase("36.6")) return "36,6";
        if (discountOwner.equalsIgnoreCase("Производитель")) return "Manufacturer";
        return discountOwner;
    }

    private static ScoRequest.PromoDiscountOne createPromoDiscountOne(RubblesRequest request) {
        ScoRequest.PromoDiscountOne promoDiscount = new ScoRequest.PromoDiscountOne();
        promoDiscount.setDiscountType("PERCENT");
        promoDiscount.setDiscountValue(request.getDiscountValue1() != null ? request.getDiscountValue1() : null);
        promoDiscount.setItemCount(request.getItemCount1() != null ? request.getItemCount1() : null);
        if(request.getScoAndOr1() != null) {
            if(request.getScoAndOr1().equals("И")) {
                promoDiscount.setScoAndOr("AND");
            } else if(request.getScoAndOr1().equals("ИЛИ")) {
                promoDiscount.setScoAndOr("OR");
            }
        }
        promoDiscount.setMinSum(request.getMinSum1() != null ? request.getMinSum1() : null);
        return promoDiscount;
    }

    private static ScoRequest.PromoDiscountTwo createPromoDiscountTwo(RubblesRequest request) {
        ScoRequest.PromoDiscountTwo promoDiscount = new ScoRequest.PromoDiscountTwo();
        promoDiscount.setDiscountType("PERCENT");
        promoDiscount.setDiscountValue(request.getDiscountValue2() != null ? request.getDiscountValue2() : null);
        promoDiscount.setItemCount(request.getItemCount2() != null ? request.getItemCount2() : null);
        if(request.getScoAndOr2() != null) {
            if(request.getScoAndOr2().equals("И")) {
                promoDiscount.setScoAndOr("AND");
            } else if(request.getScoAndOr2().equals("ИЛИ")) {
                promoDiscount.setScoAndOr("OR");
            }
        }
        promoDiscount.setMinSum(request.getMinSum2() != null ? request.getMinSum2() : null);
        return promoDiscount;
    }

    private static ScoRequest.PromoDiscountThree createPromoDiscountThree(RubblesRequest request) {
        ScoRequest.PromoDiscountThree promoDiscount = new ScoRequest.PromoDiscountThree();
        promoDiscount.setDiscountType("PERCENT");
        promoDiscount.setDiscountValue(request.getDiscountValue3() != null ? request.getDiscountValue3() : null);
        promoDiscount.setItemCount(request.getItemCount3() != null ? request.getItemCount3() : null);
        if(request.getScoAndOr3() != null) {
            if(request.getScoAndOr3().equals("И")) {
                promoDiscount.setScoAndOr("AND");
            } else if(request.getScoAndOr3().equals("ИЛИ")) {
                promoDiscount.setScoAndOr("OR");
            }
        }
        promoDiscount.setMinSum(request.getMinSum3() != null ? request.getMinSum3() : null);
        return promoDiscount;
    }

    private static ScoRequest.TimeOption createTimeOption(RubblesRequest request) {
        ScoRequest.TimeOption timeOption = new ScoRequest.TimeOption();
        timeOption.setStartDate(request.getStartDate() != null ? request.getStartDate() : ZonedDateTime.now().plusDays(request.getDaysUntilStart()));
        timeOption.setEndDate(request.getEndDate() != null ? request.getEndDate() : ZonedDateTime.now().plusDays(request.getDaysUntilEnd()));
        timeOption.setStartTime(request.getStartTime() != null ? request.getStartTime() : null);
        timeOption.setEndTime(request.getEndTime() != null ? request.getEndTime() : null);
        if(request.getWeekdayAction() != null) {
            switch (request.getWeekdayAction()) {
                case "пн":
                    timeOption.setWeekdayAction("mon");
                    break;
                case "вт":
                    timeOption.setWeekdayAction("tue");
                    break;
                case "ср":
                    timeOption.setWeekdayAction("wed");
                    break;
                case "чт":
                    timeOption.setWeekdayAction("thu");
                    break;
                case "пт":
                    timeOption.setWeekdayAction("fri");
                    break;
                case "сб":
                    timeOption.setWeekdayAction("sat");
                    break;
                case "вс":
                    timeOption.setWeekdayAction("sun");
                    break;
            }
        }
        return timeOption;
    }

    private static ScoRequest.SRN createSrn(RubblesRequest request) {
        ScoRequest.SRN srn = new ScoRequest.SRN();
        srn.setReportFlg(request.getReportFlg() ? 1L : 0L);
        srn.setDateStartSr(request.getDateStartSr() != null ? request.getDateStartSr() : null);
        srn.setDateEndSr(request.getDateEndSr() != null ? request.getDateEndSr() : null);
        if (request.getSrnFlg() != null ) {
            srn.setSrnFlg(request.getSrnFlg() ? 1L : 0L);
        }
        return srn;
    }
}
