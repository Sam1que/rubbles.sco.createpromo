package ru.a366.sco_createpromo.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.a366.sco_createpromo.db.DbAdapter;
import ru.a366.sco_createpromo.model.RubblesRequest;
import ru.a366.sco_createpromo.model.ScoResponse;
import ru.a366.sco_createpromo.model.query.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
@Slf4j
public class QueryMapper {

    @Autowired
    private DbAdapter dbAdapter;

    public static Query toRbPromoAcrmQuery(RubblesRequest request, ScoResponse response) {
        try {
            return RbPromoAcrmQuery.builder()
                    .promoId(response.getPromoId())
                    //.promptFlg(request.getPromptFlg())
                    .platformPromoId(response.getPlatformPromoId())
                    .promoName(request.getPromoName())
                    .scoTemplateNum(request.getScoTemplateNum())
                    .scoTemplateCd(request.getScoTemplateCd())
                    .aboutPromo(request.getAboutPromo())
                    .discountCounter(request.getDiscountCounter())
                    .discountOwner(request.getDiscountOwner())
                    .promoExplation(request.getPromoExplation())
                    .sumOnlineVedFlg(request.getSumOnlineVedFlg())
                    .disOnlineOrderFlg(request.getDisOnlineOrderFlg())
                    .sumOnlineReserveFlg(request.getSumOnlineReserveFlg())
                    .disOnlineReserveFlg(request.getDisOnlineReserveFlg())
                    .oldDisInFlg(request.getOldDisInFlg())
                    .fractPacFlg(request.getFractPacFlg())
                    .discountOption(request.getDiscountOption())
                    .minItemCount(request.getMinItemCount())
                    .itemCount(request.getItemCount())
                    .itemIndCount(request.getItemIndCount())
                    .itemCountInd2(request.getItemCountInd2())
                    .indOption(request.getIndOption()!=null ? request.getIndOption() : null)
                    .discountType(request.getDiscountType())
                    .discountValue(request.getDiscountValue())
                    .prizeCount(request.getPrizeCount())
                    .prizeMaxCount(request.getPrizeMaxCount())
                    .promoOnlineOrderFlg(request.getPromoOnlineOrderFlg())
                    .promoOnlineReserveFlg(request.getPromoOnlineReserveFlg())
                    .srnFlg(request.getSrnFlg())
                    .reportFlg(request.getReportFlg())
                    .dateStartSr(request.getDateStartSr())
                    .dateEndSr(request.getDateEndSr())
                    .minSum1(request.getMinSum1())
                    .minSum2(request.getMinSum2())
                    .minSum3(request.getMinSum3())
                    .discountType1(request.getDiscountType1())
                    .discountType2(request.getDiscountType2())
                    .discountType3(request.getDiscountType3())
                    .discountValue1(request.getDiscountValue1())
                    .discountValue2(request.getDiscountValue2())
                    .discountValue3(request.getDiscountValue3())
                    .scoAndOr1(request.getScoAndOr1())
                    .scoAndOr2(request.getScoAndOr2())
                    .scoAndOr3(request.getScoAndOr3())
                    .itemCount1(request.getItemCount1())
                    .itemCount2(request.getItemCount2())
                    .itemCount3(request.getItemCount3())
                    .startDate(request.getStartDate())
                    .endDate(request.getEndDate())
                    .startTime(request.getStartTime())
                    .endTime(request.getEndTime())
                    .weekdayAction(request.getWeekdayAction())
                    .scoUploadFlg(0)
                    .statusDttm(LocalDateTime.now())
                    .intStatus(response.getStatus().equalsIgnoreCase("OK") ? "CREATED" : response.getStatus().toUpperCase())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public static List<Query> toRbItemGroups(RubblesRequest request, ScoResponse response,
                                             Long itmGrId, Long intItemId) {
        List<Query> queries = new LinkedList<>();
        queries.addAll(getTypes(itmGrId, intItemId, response.getPromoId(),
                request.getArrayItmInd1(), "ind1"));
        queries.addAll(getTypes(itmGrId, intItemId, response.getPromoId(),
                request.getArrayItmInd2(), "ind2"));
        queries.addAll(getTypes(itmGrId, intItemId, response.getPromoId(),
                request.getArrayItmPrize(), "prize"));
        return queries;
    }

    public static List<Query> toRbPosGroups(RubblesRequest request, ScoResponse response,
                                            Long posGrId, Long intItemId) {
        List<Query> queries = new LinkedList<>();
        for(String pos: request.getArrayPos()) {
            PosGroupsQuery rbPosGroupQuery = new PosGroupsQuery();
            rbPosGroupQuery.setPosGrId(posGrId);
            rbPosGroupQuery.setPosId(Long.valueOf(pos));
            rbPosGroupQuery.setPromoId(response.getPromoId());
            rbPosGroupQuery.setPromoCd(response.getPromoId());
            rbPosGroupQuery.setIntQueryId(intItemId);
            rbPosGroupQuery.setPromoId(response.getPromoId());
            rbPosGroupQuery.setIntCreateDttm(LocalDateTime.now());
            rbPosGroupQuery.setIntUpdateDttm(LocalDateTime.now());
            rbPosGroupQuery.setIntStatus("NEW");
            rbPosGroupQuery.setAcrmCheckFlg(0);
            rbPosGroupQuery.setUploadScoFlg(0);
            queries.add(rbPosGroupQuery);
        }
        return queries;
    }

    private static List<Query> getTypes(Long itmGrId, Long intItemId, Long promoId,
                                        List<Long> items, String type) {
        List<Query> queries = new LinkedList<>();
        for(Long item: items) {
            ItemGroupsQuery rbItemGroupsQuery = new ItemGroupsQuery();
            rbItemGroupsQuery.setItmGrId(itmGrId);
            rbItemGroupsQuery.setItmCd(item);
            rbItemGroupsQuery.setItmGrIdInd1(type.equals("ind1") ? itmGrId : null);
            rbItemGroupsQuery.setItmGrIdInd2(type.equals("ind2") ? itmGrId : null);
            rbItemGroupsQuery.setItmGrIdPrize(type.equals("prize") ? itmGrId : null);
            rbItemGroupsQuery.setItmGrType(type);
            rbItemGroupsQuery.setIntQueryId(intItemId);
            rbItemGroupsQuery.setPromoId(promoId);
            rbItemGroupsQuery.setIntCreateDttm(LocalDateTime.now());
            rbItemGroupsQuery.setIntUpdateDttm(LocalDateTime.now());
            rbItemGroupsQuery.setIntStatus("NEW");
            rbItemGroupsQuery.setAcrmCheckFlg(0);
            rbItemGroupsQuery.setUploadScoFlg(0);
            queries.add(rbItemGroupsQuery);
        }
        return queries;
    }
}
