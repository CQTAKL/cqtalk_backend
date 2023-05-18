package com.cqtalk.service.toc.search;

import com.cqtalk.entity.search.PostSearch;
import com.cqtalk.entity.search.PostSearchVO;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface UserSearchService {

    PostSearchVO searchPosts(PostSearch postSearch) throws IOException;

}
