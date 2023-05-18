package com.cqtalk.dao.elasticsearch;

import com.cqtalk.entity.post.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository // spring提供的数据持久层的注解
public interface PostRepository extends ElasticsearchRepository<Post, Long> {

}
