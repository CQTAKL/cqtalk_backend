package com.cqtalk.controller.toc;

import com.cqtalk.annotation.AuthRequired;
import com.cqtalk.annotation.LoginRequired;
import com.cqtalk.aop.UserRequired;
import com.cqtalk.entity.comment.AddCommentDTO;
import com.cqtalk.entity.comment.DeleteCommentDTO;
import com.cqtalk.entity.comment.GetCommentDTO;
import com.cqtalk.entity.comment.GetCommentVO;
import com.cqtalk.service.toc.comment.CommentService;
import com.cqtalk.util.returnObject.ObjectResult;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "获取到相应实体类下的所有评论信息", notes = "尚未测试")
    @PostMapping("/get")
    public ObjectResult<GetCommentVO> getAllComments(@RequestBody GetCommentDTO getCommentDTO) {
        GetCommentVO getCommentVO = commentService.showAllComment(getCommentDTO);
        return ObjectResult.success(getCommentVO);
    }

    @LoginRequired
    @AuthRequired
    @ApiOperation(value = "新增加一条评论信息", notes = "尚未测试")
    @PostMapping("/add")
    public ObjectResult<String> addComment(@RequestBody AddCommentDTO addCommentDTO) throws ExecutionException, InterruptedException {
        commentService.addComment(addCommentDTO);
        return ObjectResult.success("上传评论成功。");
    }

    // TODO：请求发不出去
    @LoginRequired
    @UserRequired(entity = 3)
    @ApiOperation(value = "删除一条评论信息", notes = "尚未测试")
    @PostMapping("/delete")
    public ObjectResult<String> deleteComment(@RequestBody DeleteCommentDTO deleteCommentDTO) {
        commentService.deleteComment(deleteCommentDTO);
        return ObjectResult.success("删除评论成功。");
    }

}
