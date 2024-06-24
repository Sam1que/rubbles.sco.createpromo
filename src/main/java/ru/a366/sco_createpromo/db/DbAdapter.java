package ru.a366.sco_createpromo.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.a366.sco_createpromo.common.db.DbService;
import ru.a366.sco_createpromo.mapper.QueryMapper;
import ru.a366.sco_createpromo.model.RubblesRequest;
import ru.a366.sco_createpromo.model.ScoResponse;
import ru.a366.sco_createpromo.model.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DbAdapter {
    @Value("${db.postgres.batch_size}")
    private int DB_BATCH_SIZE;

    @Value("${db.tables.rb-promo-acrm-sco}")
    private String RB_PROMO_ACRM_SCO;

    @Value("${db.tables.rb-item-groups}")
    private String RB_ITEM_GROUPS;

    @Value("${db.tables.rb-pos-groups}")
    private String RB_POS_GROUPS;

    @Value("${db.tables.int-rb-item-list}")
    private String INT_RB_ITEM_LIST;

    @Value("${db.tables.int-post-of-sales-rb}")
    private String INT_POST_OF_SALES_RB;

    @Value("${db.seq.item-gr-id}")
    private String seqItemGrId;

    @Value("${db.seq.pos-gr-id}")
    private String seqPosGrId;

    @Value("${db.seq.int-item-id}")
    private String seqIntItemId;

    @Value("${db.seq.int-pos-id}")
    private String seqIntPosId;

    @Value("${db.sql.update-rb-promo-acrm-sco}")
    private String updatePromoAcrmSco;

    @Value("${db.postgres.batch_size}")
    private Integer BATCH_SIZE;

    @Autowired
    @Qualifier("intDbService")
    private DbService intDbService;

    @Autowired
    @Qualifier("cdmDbService")
    private DbService cdmDbService;

    private List<Map<String, Object>> getItemGrIdSeq() throws Exception{
        try {
            log.info("Getting " + seqItemGrId + "  list from the sequence");
            return cdmDbService
                    .select("SELECT nextval('" + seqItemGrId + "') as ITEM_GR_ID_SEQ from generate_series(1,1);",
                            (Map<String, Object>) null);
        }
        catch (Exception e)
        {
            throw new Exception("Error getting " + seqItemGrId + " list: " + e.getMessage());
        }
    }

    private List<Map<String, Object>> getPosGrIdSeq() throws Exception{
        try {
            log.info("Getting " + seqPosGrId + " list from the sequence");
            return cdmDbService
                    .select("SELECT nextval('" + seqPosGrId + "') as POS_GR_ID_SEQ from generate_series(1,1);",
                            (Map<String, Object>) null);
        }
        catch (Exception e)
        {
            throw new Exception("Error getting " + seqPosGrId + " list:" + e.getMessage());
        }
    }

    private List<Map<String, Object>> getSeqIntItemId() throws Exception{
        try {
            log.info("Getting " + seqIntItemId + " list from the sequence");
            return intDbService
                    .select("SELECT nextval('" + seqIntItemId + "') as INT_ITEM_ID_SEQ from generate_series(1,1);",
                            (Map<String, Object>) null);
        }
        catch (Exception e)
        {
            throw new Exception("Error getting " + seqIntItemId + " list: " + e.getMessage());
        }
    }

    private List<Map<String, Object>> getSeqIntPosId() throws Exception{
        try {
            log.info("Getting " + seqIntPosId + " list from the sequence");
            return intDbService
                    .select("SELECT nextval('" + seqIntPosId + "') as INT_POS_ID_SEQ from generate_series(1,1);",
                            (Map<String, Object>) null);
        }
        catch (Exception e)
        {
            throw new Exception("Error getting " + seqIntPosId + " list: " + e.getMessage());
        }
    }

    private void insertIntoRbPromoAcrmSco(RubblesRequest request, ScoResponse response) throws Exception {
        log.info("Inserting into " + RB_PROMO_ACRM_SCO);
        try {
            Query queries = QueryMapper.toRbPromoAcrmQuery(request, response);
            cdmDbService.simpleBatchInsert(RB_PROMO_ACRM_SCO, List.of(queries), BATCH_SIZE);
        } catch (Exception e) {
            throw new Exception("Error inserting into table "+ RB_PROMO_ACRM_SCO +": "+ e);
        }
    }

    private void insertIntoRbItemGroups(RubblesRequest request, ScoResponse response) throws Exception {
        List<Query> queries = QueryMapper.toRbItemGroups(request, response,
                (Long) getItemGrIdSeq().get(0).get("ITEM_GR_ID_SEQ"),
                (Long) getSeqIntItemId().get(0).get("INT_ITEM_ID_SEQ"));
        try {
            log.info("Inserting into " + RB_ITEM_GROUPS);
            cdmDbService.simpleBatchInsert(RB_ITEM_GROUPS, queries, queries.size());
        } catch (Exception e) {
            throw new Exception("Error inserting into table "+ RB_ITEM_GROUPS +": "+ e);
        }
        try {
            log.info("Inserting into " + INT_RB_ITEM_LIST);
            intDbService.simpleBatchInsert(INT_RB_ITEM_LIST, queries, queries.size());
        } catch (Exception e) {
            throw new Exception("Error inserting into table "+ INT_RB_ITEM_LIST +": "+ e);
        }
    }

    private void insertIntoRbPosGroups(RubblesRequest request, ScoResponse response) throws Exception {
        List<Query> queries = QueryMapper.toRbPosGroups(request, response,
                (Long) getPosGrIdSeq().get(0).get("POS_GR_ID_SEQ"),
                (Long) getSeqIntPosId().get(0).get("SEQ_INT_POS_ID"));
        try {
            log.info("Inserting into " + RB_POS_GROUPS);
            cdmDbService.simpleBatchInsert(RB_POS_GROUPS, queries, queries.size());
        } catch (Exception e) {
            throw new Exception("Error inserting into table "+ RB_POS_GROUPS +": "+ e);
        }
        try {
            log.info("Inserting into " + INT_POST_OF_SALES_RB);
            intDbService.simpleBatchInsert(INT_POST_OF_SALES_RB, queries, queries.size());
        }  catch (Exception e) {
            throw new Exception("Error inserting into table "+ INT_POST_OF_SALES_RB +": "+ e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void processQueries(RubblesRequest request, ScoResponse response) throws Exception {
        try {
            insertIntoRbPromoAcrmSco(request, response);
            insertIntoRbItemGroups(request, response);
            insertIntoRbPosGroups(request, response);
            Map<String, Object> data = new HashMap<>();
            data.put("promo_id", response.getPromoId());
            cdmDbService.batchUpdate(updatePromoAcrmSco.replace("&rb_promo_acrm_sco", RB_PROMO_ACRM_SCO), List.of(data));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
