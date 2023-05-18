package com.cqtalk.service.toc.search.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import com.cqtalk.entity.post.Post;
import com.cqtalk.entity.search.PostSearch;
import com.cqtalk.entity.search.PostSearchInfo;
import com.cqtalk.entity.search.PostSearchVO;
import com.cqtalk.service.toc.search.UserSearchService;
import com.cqtalk.util.es.ElasticSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserSearchServiceImpl implements UserSearchService {

    @Autowired
    private ElasticsearchClient esClient;

    @Autowired
    private ElasticSearchUtil elasticSearchUtil;

    @Override
    public PostSearchVO searchPosts(PostSearch postSearch) throws IOException {
        List<PostSearchInfo> lists = elasticSearchUtil.searchPosts("post", postSearch.getKeywords(), postSearch.getType());
        PostSearchVO postSearchVO = new PostSearchVO();
        postSearchVO.setPostSearchInfoList(lists);
        postSearchVO.setCount(lists.size());
        return postSearchVO;
    }

}
