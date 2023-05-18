package com.cqtalk.controller.toc;

import com.cqtalk.entity.search.PostSearch;
import com.cqtalk.entity.search.vo.PostSearchVO;
import com.cqtalk.service.toc.search.UserSearchService;
import com.cqtalk.util.returnObject.ObjectResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private UserSearchService userSearchService;

    @ApiOperation(value = "用户搜索帖子和专栏", notes = "尚未测试")
    @PostMapping("/post")
    public ObjectResult<PostSearchVO> uploadFile(@RequestBody PostSearch postSearch) throws Exception {
        PostSearchVO postSearchVO = userSearchService.searchPosts(postSearch);
        return ObjectResult.success(postSearchVO);
    }


}
