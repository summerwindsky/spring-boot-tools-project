package com.company.project.system.es;

import com.thunisoft.elasticsearch.ElasticReader;
import com.thunisoft.elasticsearch.ElasticWriter;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.join.query.HasParentQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Title: BatchInsert
 * Description:
 * Company: 北京华宇元典信息服务有限公司
 *
 * @author lyf
 * @version 1.0
 * @date 2017年3月31日 上午9:21:07
 */
@Component
public class BatchDeal {
    private static Logger logger = LoggerFactory.getLogger(BatchDeal.class);

    /**
     * 以下是es相关配置
     */
    private static ElasticWriter esWriter = null;

    private static ElasticReader esReader = null;

    private static String cluster;

    private static String ip;

    private static int port;

    @Value("${es.cluster}")
    public void setCluster(String cluster) {
        BatchDeal.cluster = cluster;
    }

    @Value("${es.ip}")
    public void setIp(String ip) {
        BatchDeal.ip = ip;
    }

    @Value("${es.port}")
    public void setPort(int port) {
        BatchDeal.port = port;
    }

    public void init() {
        esWriter = new ElasticWriter(cluster, ip, port);
        esReader = new ElasticReader(cluster, ip, port);
    }

    public static ElasticWriter getESWriter() {
        try {
            if (esWriter == null) {
                esWriter = new ElasticWriter(cluster, ip, port);

            }
        } catch (Throwable e) {
            logger.error("失败！", e);
        }
        return esWriter;
    }

    public static ElasticReader getESReader() {
        if (esReader == null) {
            esReader = new ElasticReader(cluster, ip, port);
        }
        return esReader;
    }

    /**
     * 之前的elasticsearch性能问题可能是结构化数据中的长的base64编码导致,不是批量导致，恢复批量尝试
     * @param esWriter
     * @param list
     */
    //    public static void batchExecute(ElasticWriter esWriter, List<BatchBean> list) {
    //        if (esWriter == null) {
    //            esWriter = getESWriter();
    //        }
    //        BulkRequestBuilder builder = esWriter.getBulkRequestBuilder();
    //        for (BatchBean bean : list) {
    //            try {
    //                if (bean.getFlag().equals(TypeEnum.DELETE)) {
    //                    esWriter.delete(bean.getIndex(), bean.getType(), bean.getId());
    //                } else if (bean.getFlag().equals(TypeEnum.UPDATE)) {
    //                    
    //                } else {
    //                    esWriter.index(bean.getIndex(), bean.getType(), bean.getFid(), bean.getId(), bean.getJson());
    //                }
    //            } catch (Exception e) {
    //                logger.error(bean.getFlag().name() + " id: " + bean.getId() + " failed. index: " + bean.getIndex()
    //                        + "type: " + bean.getType());
    //                logger.error("cause by:" + e.getMessage());
    //            }
    //        }
    //    }

    /**
     * batch deal：insert/update/delete
     * 批量操作
     */
    public static void batchExecute(ElasticWriter esWriter, List<BatchBean> list) {
        if (esWriter == null) {
            esWriter = getESWriter();
        }
        BulkRequestBuilder builder = esWriter.getBulkRequestBuilder();
        for (BatchBean bean : list) {
            try {
                if (bean.getFlag().equals(BatchBean.TypeEnum.DELETE)) {
                    builder.add(esWriter.getDeleteRequestBuilder(bean.getIndex(), bean.getType(), bean.getId()));
                } else if (bean.getFlag().equals(BatchBean.TypeEnum.UPDATE)) {
                    builder.add(esWriter.getUpdateRequestBuilder(bean.getIndex(), bean.getType(), bean.getId(),
                            bean.getJson()));
                } else {
                    builder.add(esWriter.getIndexRequestBuilder(bean.getIndex(), bean.getType(), bean.getFid(),
                            bean.getId(), bean.getJson()));
                }
            } catch (Exception e) {
                logger.error(bean.getFlag().name() + " id: " + bean.getId() + " failed. index: " + bean.getIndex()
                        + " type: " + bean.getType());
                logger.error("cause by:" + e.getMessage());
            }
        }
        BulkResponse resp = builder.execute().actionGet();
        if (resp.hasFailures()) {
            Iterator<BulkItemResponse> iter = resp.iterator();
            while (iter.hasNext()) {
                BulkItemResponse r = iter.next();
                if (r != null && r.isFailed()) {
                    logger.error("deal id: " + r.getId() + " failed. index: " + r.getIndex() + " type: " + r.getType());
                    //slf4j如果需要输出error信息请使用{}，不然信息不打印
                    logger.error("cause by:{}", r.getFailure().getMessage() + "|" + r.getFailureMessage());
                }
            }
        }
    }

    /**
     * batch deal：insert/update/delete
     */
    public static void batchExecute(List<BatchBean> list) {
        batchExecute(null, list);
    }

    public static List<String> searchAllYsId(ElasticReader esReader, String index, String type, String caseid) {
        List<String> resultIds = new ArrayList<String>();
        if (esReader == null) {
            esReader = getESReader();
        }
        //es的查询构造
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.size(20);
        builder.storedField("_id");
        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery("caseId:" + caseid);
        builder.query(queryBuilder);
        //es的查询
        SearchRequest searchRequest = esReader.getSearchRequest(index);
        searchRequest.scroll(new TimeValue(300000L));
        searchRequest.source(builder);
        SearchResponse response = esReader.search(searchRequest);
        String scrollId = response.getScrollId();
        while (scrollId != null) {
            response = esReader.searchScroll(scrollId, 300000L);
            scrollId = response.getScrollId();
            SearchHits results = response.getHits();
            if (results.getHits().length > 0) {
                for (SearchHit hit : results.getHits()) {
                    String ysId = hit.getId();
                    resultIds.add(ysId);
                }
            } else {
                break;
            }
        }
        return resultIds;
    }

    public static List<String> searchAllYsId(String index, String type, String caseid) {
        return searchAllYsId(null, index, type, caseid);
    }

    /**
     * 查询index里面的id的所有子文档
     */
    public static void searchAllChild(ElasticReader esReader, Long timeout, int size, String index, String parentType,
                                      String id, List<String> types, List<String> ids) {
        if (esReader == null) {
            esReader = getESReader();
        }
        if (timeout == null) {
            timeout = 300000L;
        }
        if (size == 0) {
            size = 20;
        }
        //es的查询构造
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.size(size);
        builder.storedField("_id");

        QueryBuilder innerQuery = QueryBuilders.termQuery("_id", id);
        QueryBuilder queryBuilder = new HasParentQueryBuilder(parentType, innerQuery, false);
        builder.query(queryBuilder);
        //es的查询
        SearchRequest searchRequest = esReader.getSearchRequest(index);
        searchRequest.scroll(new TimeValue(timeout));
        searchRequest.source(builder);
        SearchResponse response = esReader.search(searchRequest);
        //es的结果获取
        //logger.warn("{} id has {} childDoc", id, response.getHits().getTotalHits());

        String scrollId = response.getScrollId();
        while (scrollId != null) {
            response = esReader.searchScroll(scrollId, timeout);
            scrollId = response.getScrollId();
            SearchHits results = response.getHits();
            if (results.getHits().length > 0) {
                for (SearchHit hit : results.getHits()) {
                    String childId = hit.getId();
                    String childType = hit.getType();
                    types.add(childType);
                    ids.add(childId);
                }
            } else {
                break;
            }
        }
    }

    /**
     * 查询index里面的id的所有子文档
     */
    public static void searchAllChild(Long timeout, int size, String index, String parentType, String id,
                                      List<String> types, List<String> ids) {
        searchAllChild(null, timeout, size, index, parentType, id, types, ids);
    }

    /**
     * 查询index里面的id的所有子文档
     */
    public static void searchAllChild(String index, String parentType, String id, List<String> types,
                                      List<String> ids) {
        searchAllChild(null, null, 0, index, parentType, id, types, ids);
    }

    /**
     * 查询index里面的id的所有子文档
     */
    public static void searchAllChild(ElasticReader esReader, String index, String parentType, String id,
                                      List<String> types, List<String> ids) {
        searchAllChild(esReader, null, 0, index, parentType, id, types, ids);
    }

    public static void main(String[] args) {
        BatchDeal da = new BatchDeal();
        ElasticWriter esWriter = da.getESWriter();
    }

}