package com.example.es.temp2;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilders;

import com.example.es.EsClient;

/**
 * Created by ZhangDong on 2016/1/5.
 */
public class SearchService2 {

    Log log = LogFactory.getLog(getClass());
    public SearchResponse getSimpleSearchResponse( int page, int pagesize){

        BoolQueryBuilder mustQuery = QueryBuilders.boolQuery();
        mustQuery.must(QueryBuilders.matchAllQuery()); // 添加第1条must的条件 此处为匹配所有文档

        mustQuery.must(QueryBuilders.matchPhraseQuery("title", "时间简史"));//添加第2条must的条件 title字段必须为【时间简史】
        // ↑ 放入筛选条件(termQuery为精确搜索，大小写敏感且不支持*) 实验发现matchPhraseQuery可对中文精确匹配term

        mustQuery.must(QueryBuilders.matchQuery("auther", "霍金")); // 添加第3条must的条件

        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery("物理")//.escape(true)//escape 转义 设为true，避免搜索[]、结尾为!的关键词时异常 但无法搜索*
                .defaultOperator(QueryStringQueryBuilder.Operator.AND);//不同关键词之间使用and关系
        mustQuery.must(queryBuilder);//添加第4条must的条件 关键词全文搜索筛选条件

        SearchRequestBuilder searchRequestBuilder = EsClient.getClient().prepareSearch("index name").setTypes("type name")
                .setQuery(mustQuery)
                .addHighlightedField("*")/*星号表示在所有字段都高亮*/.setHighlighterRequireFieldMatch(false)//配置高亮显示搜索结果
                .setHighlighterPreTags("<高亮前缀标签>").setHighlighterPostTags("<高亮后缀标签>");//配置高亮显示搜索结果

                searchRequestBuilder = searchRequestBuilder.addAggregation(AggregationBuilders.terms("agg1(聚类返回时根据此key获取聚类结果)")
                        .size(1000)/*返回1000条聚类结果*/.field("要在文档中聚类的字段，如果是嵌套的则用点连接父子字段，如【person.company.name】"));

        SearchResponse searchResponse = searchRequestBuilder.setFrom((page - 1) * pagesize)//分页起始位置（跳过开始的n个）
                .setSize(pagesize)//本次返回的文档数量
                .execute().actionGet();//执行搜索

        log.info("response="+searchResponse);
        return searchResponse;
    }
}