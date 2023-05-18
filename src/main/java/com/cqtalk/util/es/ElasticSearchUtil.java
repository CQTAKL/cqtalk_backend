package com.cqtalk.util.es;

import co.elastic.clients.elasticsearch.ElasticsearchClient;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.json.JsonData;
import com.cqtalk.entity.post.Post;
import com.cqtalk.entity.search.PostSearchInfo;
import com.cqtalk.util.returnObject.DescribeException;
import com.cqtalk.util.returnObject.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ElasticSearchUtil {

    @Autowired
    private ElasticsearchClient esClient;

    private Query initMatchQuery(String fieldName, String fieldValue) {
        return StringUtils.isNotBlank(fieldValue) ? MatchQuery.of(m -> m
                .field(fieldName + ".keyword")
                .query(fieldValue)
        )._toQuery() : null;
    }

    private Query initIntegerMatchQuery(String fieldName, Integer fieldValue) {
        return fieldValue != null ? RangeQuery.of(m -> m
                .field(fieldName + ".keyword")
                .gte(JsonData.of(fieldValue))
                .lte((JsonData.of(fieldValue)))
        )._toQuery() : null;
    }

    // 关键字高亮查询, 设置参数：要按什么方式查询
    public List<PostSearchInfo> searchPosts(String indexName, String keywords, Integer type) throws IOException {
        /*List<Query> queries = new ArrayList<>();
        Query byTitle = this.initMatchQuery("title", keywords);
        Query byContent = this.initMatchQuery("content", keywords);
        // Query byBriefIntro = this.initMatchQuery("briefIntro", keywords);
        Query byType = this.initIntegerMatchQuery("type", type);
        queries.add(byTitle);
        queries.add(byContent);
        // queries.add(byBriefIntro);
        // queries.add(byType);*/
        HitsMetadata<PostSearchInfo> searchResponse;
        try {
            // 从es中根据条件查询结果.
            searchResponse = esClient.search(s -> {s
                    .index(indexName)
                    .query(
                            q -> q.bool(b ->
                                    b.should(h -> h.match(u -> u.field("title").query(keywords)))
                                            .should(h -> h.match(u -> u.field("content").query(keywords)))
                                            .should(h -> h.match(u -> u.field("briefIntro").query(keywords)))
                                            .must(h -> h.match(u -> u.field("type").query(type)))
                            ));
                /*q -> q.match(
                                    m -> m.field("title").query(keywords)
                            )*/
            return s;}, PostSearchInfo.class).hits();
        } catch(Exception e) {
            throw new DescribeException(ErrorCode.ES_SEARCH_POST_ERROR.getCode(), ErrorCode.ES_SEARCH_POST_ERROR.getMsg());
        }
        List<PostSearchInfo> lists = new ArrayList<>();
        for(Hit<PostSearchInfo> hit : searchResponse.hits()) {
            assert hit.source() != null;
            PostSearchInfo postSearchInfo = new PostSearchInfo(hit.source());
            lists.add(postSearchInfo);
        }
        return lists;
/*
        List<Post> postList = new ArrayList<>();
        for(Hit<Post> hit : search.hits().hits()) {
            assert hit.source() != null;
            Post post = new Post(hit.source());
            post.setTitle(hit.highlight().get("title.ik_max_analyzer").toString().replace("[","").replace("]",""));// 替换为高亮的标题
            postList.add(post);
        }
        System.out.println(postList);
        return postList;*/
    }

    // 在es中添加记录
    public void insertESPost(PostSearchInfo postSearchInfo) {
        try {
            esClient.index(i -> i
                    .index("post")
                    .id(postSearchInfo.getEntityId().toString())
                    .document(postSearchInfo));
        } catch (Exception e) {
            throw new DescribeException(ErrorCode.ES_INSERT_POST_ERROR.getCode(), ErrorCode.ES_INSERT_POST_ERROR.getMsg());
        }
    }

}
