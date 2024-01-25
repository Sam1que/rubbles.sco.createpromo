package ru.a366.sco_createpromo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import ru.a366.sco_createpromo.db.DbAdapter;
import ru.a366.sco_createpromo.model.RubblesRequest;
import ru.a366.sco_createpromo.model.ScoResponse;
import ru.a366.sco_createpromo.model.query.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class QueryMapper {

    @Autowired
    private DbAdapter dbAdapter;

    public static Query toRbPromoAcrmQuery(RubblesRequest request, ScoResponse response) {
        try {
            return RbPromoAcrmQuery.builder()
                    .promoId(response.getPromoId())
                    .promoName(request.getPromoName())
                    .scoTemplateNum(request.getScoTemplateNum())
                    .scoTemplateCd(request.getScoTemplateCd())
                    .aboutPromo(request.getAboutPromo())
                    .discountCounter(request.getDiscountCounter())
                    .discountOwner(request.getDiscountOwner())
                    .promoExplation(request.getPromoExplation())
                    .sumOnlineVedFlg(request.getSumOnlineVedFlg() ? 1L : 0L) // Не бьется Long / Boolean
                    .disOnlineOrderFlg(request.getDisOnlineOrderFlg() ? 1L : 0L) // Не бьется Long / Boolean
                    .sumOnlineReserveFlg(request.getSumOnlineReserveFlg() ? 1L : 0L)
                    .disOnlineReserveFlg(request.getDisOnlineReserveFlg() ? 1L : 0L)
                    .oldDisInFlg(request.getOldDisInFlg() ? 1L : 0L)
                    .fractPacFlg(request.getFractPacFlg() ? 1L : 0L)
                    .discountOption(request.getDiscountOption())
                    .minItemCount(request.getMinItemCount())
                    .itemCount(request.getItemCount())
                    .itemIndCount(request.getItemIndCount())
                    .itemCountInd2(request.getItemCountInd2())
                    .indOption(request.getIndOption())
                    .discountType(request.getDiscountType())
                    .discountValue(request.getDiscountValue())
                    .prizeCount(request.getPrizeCount())
                    .prizeMaxCount(request.getPrizeMaxCount())
                    .promoOnlineOrderFlg(request.getPromoOnlineOrderFlg() ? 1L : 0L)
                    .promoOnlineReserveFlg(request.getPromoOnlineReserveFlg() ? 1L : 0L)
                    .srnFlg(request.getSrnFlg() ? 1L : 0L)
                    .reportFlg(request.getReportFlg() ? 1L : 0L)
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
                    .weakdayAction(request.getWeekdayAction())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public static List<Query> toRbItemGroups(RubblesRequest request, ScoResponse response,
                                             Long itmGrId, Long seqIntItemId) {
        List<Query> queries = new LinkedList<>();
        for(String item: request.getItems()) {
            ItemGroupsQuery rbItemGroupsQuery = new ItemGroupsQuery();
            rbItemGroupsQuery.setItmGrId(itmGrId);
            rbItemGroupsQuery.setItmCd(Long.valueOf(item)); // Не бьется String / Long
            rbItemGroupsQuery.setItmGrIdInd1(request.getItmGrType().equals("ind1") ? itmGrId : null);
            rbItemGroupsQuery.setItmGrIdInd2(request.getItmGrType().equals("ind2") ? itmGrId : null);
            rbItemGroupsQuery.setItmGrIdPrize(request.getItmGrType().equals("prize") ? itmGrId : null);
            rbItemGroupsQuery.setItmGrType(request.getItmGrType());
            rbItemGroupsQuery.setIntQueryId(seqIntItemId);
            rbItemGroupsQuery.setPromoId(response.getPromoId());
            rbItemGroupsQuery.setIntCreateDttm(LocalDateTime.now());
            rbItemGroupsQuery.setIntUpdateDttm(LocalDateTime.now());
            rbItemGroupsQuery.setIntStatus("NEW");
            rbItemGroupsQuery.setAcrmCheckFlg(0);
            queries.add(rbItemGroupsQuery);
        }
        return queries;
    }

    public static List<Query> toRbPosGroups(RubblesRequest request, ScoResponse response,
                                           Long posGrIdSeq, Long seqIntPosId) {
        List<Query> queries = new LinkedList<>();
        for(String pos: request.getPos()) {
            PosGroupsQuery rbPosGroupQuery = new PosGroupsQuery();
            rbPosGroupQuery.setPosGrId(posGrIdSeq);
            rbPosGroupQuery.setPosId(Long.valueOf(pos));
            rbPosGroupQuery.setPromoId(response.getPromoId());
            rbPosGroupQuery.setPromoCd(response.getPromoId());
            rbPosGroupQuery.setIntQueryId(seqIntPosId);
            rbPosGroupQuery.setPromoId(response.getPromoId());
            rbPosGroupQuery.setIntCreateDttm(LocalDateTime.now());
            rbPosGroupQuery.setIntUpdateDttm(LocalDateTime.now());
            rbPosGroupQuery.setIntStatus("NEW");
            rbPosGroupQuery.setAcrmCheckFlg(0);
            queries.add(rbPosGroupQuery);
        }
        return queries;
    }
}
