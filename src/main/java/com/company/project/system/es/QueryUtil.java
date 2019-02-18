package com.company.project.system.es;

import com.company.project.system.Constants;
import com.thunisoft.elasticsearch.ElasticReader;
import com.thunisoft.elasticsearch.ElasticWriter;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gaojun
 * @Title QueryUtil
 * @Description TODO
 * @date 2016-8-16 下午3:40:35
 * @Company 北京华宇信息技术有限公司
 */
@Component
public class QueryUtil {

    private static final Logger logger = LoggerFactory.getLogger(QueryUtil.class);

    private static ElasticWriter esWriter = null;

    private static ElasticReader esReader = null;

    private static String cluster;

    private static String ip;

    private static int port;

    @Value("${es.cluster}")
    public void setCluster(String cluster) {
        QueryUtil.cluster = cluster;
    }

    @Value("${es.ip}")
    public void setIp(String ip) {
        QueryUtil.ip = ip;
    }

    @Value("${es.port}")
    public void setPort(int port) {
        QueryUtil.port = port;
    }

    @PostConstruct
    public void init() {
        esWriter = new ElasticWriter(cluster, "elastic:changeme", ip, port);
        esReader = new ElasticReader(cluster, "elastic:changeme", ip, port);
    }

    /**
     * 根据参数构造搜索条件
     *
     * @param query
     * @param rFields
     * @return
     */
    public static SearchSourceBuilder getSearchSourceBuilder(QueryBuilder query, List<String> rFields) {
        SearchSourceBuilder searchBuilder = new SearchSourceBuilder();
        searchBuilder.query(query);
        if (!CollectionUtils.isEmpty(rFields)) {
            for (String field : rFields) {
                searchBuilder.docValueField(field);
            }
        }
        return searchBuilder;
    }

    /**
     * 刑事案件
     * XSAJ_YSAJ
     * XSAJ_ESAJ
     * XSAJ_ZSAJ
     * <p>
     * 民事案件
     * MSAJ_YSAJ
     * MSAJ_ESAJ
     * MSAJ_ZSAJ
     * <p>
     * 行政案件
     * XZAJ_YSAJ
     * XZAJ_ESAJ
     * XZAJ_ZSAJ
     * 存在律所冗余成员，及律所的名称的结果记录
     *
     * @return
     */
    public static QueryBuilder getCaseQuery() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.existsQuery("XSAJ_YSAJ.QW.DSR.CUS_LSCY_RY.CUS_LSCY.CUS_LSDW.@value"))
                .should(QueryBuilders.existsQuery("XSAJ_ESAJ.QW.DSR.CUS_LSCY_RY.CUS_LSCY.CUS_LSDW.@value"))
                .should(QueryBuilders.existsQuery("XSAJ_ZSAJ.QW.DSR.CUS_LSCY_RY.CUS_LSCY.CUS_LSDW.@value"))

                .should(QueryBuilders.existsQuery("MSAJ_YSAJ.QW.DSR.CUS_LSCY_RY.CUS_LSCY.CUS_LSDW.@value"))
                .should(QueryBuilders.existsQuery("MSAJ_ESAJ.QW.DSR.CUS_LSCY_RY.CUS_LSCY.CUS_LSDW.@value"))
                .should(QueryBuilders.existsQuery("MSAJ_ZSAJ.QW.DSR.CUS_LSCY_RY.CUS_LSCY.CUS_LSDW.@value"))

                .should(QueryBuilders.existsQuery("XZAJ_YSAJ.QW.DSR.CUS_LSCY_RY.CUS_LSCY.CUS_LSDW.@value"))
                .should(QueryBuilders.existsQuery("XZAJ_ESAJ.QW.DSR.CUS_LSCY_RY.CUS_LSCY.CUS_LSDW.@value"))
                .should(QueryBuilders.existsQuery("XZAJ_ZSAJ.QW.DSR.CUS_LSCY_RY.CUS_LSCY.CUS_LSDW.@value"))
                .minimumShouldMatch(1);

        return boolQueryBuilder;
    }

    /**
     * 增量数据，取指定时间戳之后的数据
     *
     * @param fromTimestamp
     * @return
     */
    public static QueryBuilder getTimedCaseQuery(long fromTimestamp) {
        BoolQueryBuilder boolQuery = (BoolQueryBuilder) getCaseQuery();
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("timestamp").gte(fromTimestamp);
        boolQuery.filter(rangeQuery);
        return boolQuery;
    }

    public static List<String> getCaseFields() {
        List<String> fields = new ArrayList<String>();
        fields.add("AH_LIST");
        return fields;
    }

    public static SearchRequest getSearchRequest(String index, String type, SearchSourceBuilder searchBuilder) {
        searchBuilder.size(Constants.es_scroll_size);
        SearchRequest searchRequest = esReader.getSearchRequest(index, type);
        searchRequest.source(searchBuilder).searchType(SearchType.QUERY_THEN_FETCH)
                .scroll(new TimeValue(Constants.es_scroll_timeout));
        return searchRequest;
    }

    public static SearchRequest getCaseSearchRequest() {
        SearchSourceBuilder searchBuilder = QueryUtil.getSearchSourceBuilder(getCaseQuery(), getCaseFields());
        return getSearchRequest(Constants.es_index, Constants.es_case_type, searchBuilder);
    }

    public static SearchRequest getTimedCaseSearchRequest(long fromTimestamp) {
        SearchSourceBuilder searchBuilder = QueryUtil.getSearchSourceBuilder(getTimedCaseQuery(fromTimestamp),
                getCaseFields());
        return getSearchRequest(Constants.es_index, Constants.es_case_type, searchBuilder);
    }

    public static SearchRequest getWritAnalyzerSearchRequest(String _id) {
        return getSearchRequestByID(_id, Constants.es_index, Constants.es_writAnalyzer_type);
    }

    public static SearchRequest getSearchRequestByID(String _id, String index, String type) {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("_id", _id));
        searchSourceBuilder.version(true);

        SearchRequest searchRequest = esReader.getSearchRequest(index, type);
        searchRequest.source(searchSourceBuilder);

        return searchRequest;
    }

    public static SearchHit[] getCaseWritAnalyzerSearchHitByAh(String ah, String caseID) {

        BoolQueryBuilder builder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("AH", ah)).must(QueryBuilders.termQuery("CASEID", caseID));

        SearchResponse writResponse = doSearch(Constants.es_index, Constants.es_writAnalyzer_type, builder);

        SearchHits writResult = writResponse.getHits();

        return writResult.getHits();
    }

    public static SearchHit[] getCaseWritAnalyzerSearchHitByAh(String ah) {

        BoolQueryBuilder builder = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("AH", ah));

        SearchResponse writResponse = doSearch(Constants.es_index, Constants.es_writAnalyzer_type, builder);

        SearchHits writResult = writResponse.getHits();

        return writResult.getHits();
    }

    public static SearchHit getCaseWritAnalyzerSearchHitByID(String _id) {
        return getSearchHitByTerm(Constants.es_index, Constants.es_writAnalyzer_type, "_id", _id);
    }

    public static SearchHit getTJWritAnalyzerSearchHitByID(String _id) {
        return getSearchHitByTerm(Constants.es_index_tj, Constants.es_writAnalyzer_type, "_id", _id);
    }

    public static SearchHit[] getSearchHitByTerms(String index, String type, String termName, String termValue) {
        SearchHit searchHit = null;

        SearchResponse writResponse = doSearch(index, type, QueryBuilders.termQuery(termName, termValue));

        SearchHits writResult = writResponse.getHits();

        return writResult.getHits();

    }

    public static SearchHit getSearchHitByTerm(String index, String type, String termName, String termValue) {
        SearchHit searchHit = null;

        SearchResponse writResponse = doSearch(index, type, QueryBuilders.termQuery(termName, termValue));

        SearchHits writResult = writResponse.getHits();

        SearchHit[] searchHitArray = writResult.getHits();

        if (searchHitArray != null && searchHitArray.length > 0) {
            searchHit = searchHitArray[0];
        }
        return searchHit;
    }

    public static SearchHits getSearchHitByIDs(String index, String type, List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }
        String[] idsArr = new String[ids.size()];
        ids.toArray(idsArr);
        SearchResponse writResponse = doSearch(index, type, QueryBuilders.idsQuery(type).addIds(idsArr));
        return writResponse.getHits();
    }

    public static SearchResponse doSearch(String index, String type, QueryBuilder queryBuilder) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.version(true);

        SearchRequest searchRequest = esReader.getSearchRequest(index, type);
        searchRequest.source(searchSourceBuilder);

        return esReader.search(searchRequest);
    }

    public static void index(String index, String type, String pid, String id, Object source, boolean refresh) {
        IndexResponse r = esWriter.index(index, type, pid, id, source, refresh);
//        RestStatus status = r.status();
//        if (status != RestStatus.OK) {
//            logger.error("deal id: " + r.getId() + " failed. index: " + r.getIndex() + " type: " + r.getType()
//                    + "result status:{}" + r.status());
//        }
    }

    public static void update(String index, String type, String id, Object source, boolean refresh) {
        UpdateRequestBuilder builder = esWriter.getEsClient().getClient().prepareUpdate(index, type, id);
        if (source instanceof Map) {
            builder.setDoc((Map) source);
        } else if (source instanceof String) {
            builder.setDoc((String) source, XContentFactory.xContentType((String) source));
        }

        if (refresh) {
            builder.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        }
        UpdateResponse r = builder.get();
        RestStatus status = r.status();
        if (status != RestStatus.OK) {
            logger.error("deal id: " + r.getId() + " failed. index: " + r.getIndex() + " type: " + r.getType());
            logger.error("result status:{}", r.status());
        }
    }

    public static SearchResponse getSearchResponse(String index, String type, String filed, String value) {
        ElasticReader reader = QueryUtil.getEsReader();
        QueryBuilder queryString = QueryBuilders.queryStringQuery(value).field(filed);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryString);
        SearchRequest searchRequest = esReader.getSearchRequest(index, type);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = reader.search(searchRequest);
        return searchResponse;
    }

    public static ElasticReader getEsReader() {
        return esReader;
    }

    public static ElasticWriter getEsWriter() {
        return esWriter;
    }


}
