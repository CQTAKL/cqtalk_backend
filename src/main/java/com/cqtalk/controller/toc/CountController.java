package com.cqtalk.controller.toc;

import com.cqtalk.annotation.LoginRequired;
import com.cqtalk.entity.count.*;
import com.cqtalk.service.toc.entity.CountService;
import com.cqtalk.util.jwt.JWTUtil;
import com.cqtalk.util.returnObject.ObjectResult;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/count")
public class CountController {

    private static final Logger logger = LoggerFactory.getLogger(FileFunctionController.class);

    @Autowired
    private CountService countService;

    @Autowired
    private JWTUtil jwtUtil;

    /** 点赞相关 **/

    @LoginRequired
    @ApiOperation(value = "给相关实体类点赞", notes = "尚未测试")
    @PostMapping("/like")
    public ObjectResult<String> like(@RequestBody EntityLikeDTO entityLikeDTO) {
        countService.like(entityLikeDTO);
        return ObjectResult.success("点赞成功");
    }

    @ApiOperation(value = "返回实体类的点赞数据", notes = "尚未测试")
    @PostMapping("/likeCount")
    public ObjectResult<Long> getLikeCount(@RequestBody EntityLikeCountDTO entityLikeCountDTO) {
        long likeCount = countService.findEntityLikeCount(entityLikeCountDTO);
        return ObjectResult.success(likeCount);
    }

    @LoginRequired
    @ApiOperation(value = "返回用户对该实体类的点赞状态", notes = "尚未测试")
    @PostMapping("/likeState")
    public ObjectResult<Integer> getLikeCount(@RequestBody EntityLikeDTO entityLikeDTO) {
        int likeState = countService.findEntityLikeStatus(entityLikeDTO);
        return ObjectResult.success(likeState);
    }

    /** 收藏相关 **/
    // 实现方式参考微信
    @LoginRequired
    @ApiOperation(value = "用户收藏一个实体类", notes = "尚未开发")
    @PostMapping("/collect")
    public ObjectResult<String> collect(@RequestBody EntityCollectDTO entityCollectDTO) {
        countService.collect(entityCollectDTO);
        return ObjectResult.success("收藏成功");
    }

    // 实现方式参考微信
    // TODO: 后期适当增加索引，此接口暂时不使用
    @LoginRequired
    @ApiOperation(value = "返回用户收藏的所有标签的名称", notes = "尚未开发")
    @PostMapping("/collect/label")
    public ObjectResult<EntityCollectLabelVO> collectLabel() {
        EntityCollectLabelVO entityCollectLabelVO = countService.userCollectLabel();
        return ObjectResult.success(entityCollectLabelVO);
    }

    @ApiOperation(value = "返回某个实体类的收藏数量", notes = "尚未测试")
    @PostMapping("/collectCount")
    public ObjectResult<Long> getCollectCount(@RequestBody EntityCollectCountDTO entityCollectCountDTO) {
        long count = countService.findEntityCollectCount(entityCollectCountDTO);
        return ObjectResult.success(count);
    }

    @LoginRequired
    @ApiOperation(value = "返回某个用户对某个文件的收藏状态", notes = "尚未测试")
    @PostMapping("/collectState")
    public ObjectResult<Integer> collectState(@RequestBody EntityCollectStateDTO entityCollectStateDTO) {
        Integer state = countService.findCollectState(entityCollectStateDTO);
        return ObjectResult.success(state);
    }

    @LoginRequired
    @ApiOperation(value = "用户取消收藏", notes = "尚未测试")
    @PostMapping("/uncollected")
    public ObjectResult<String> uncollected(@RequestBody EntityUnCollectDTO entityUnCollectDTO) {
        countService.uncollected(entityUnCollectDTO);
        return ObjectResult.success("取消收藏成功");
    }

    @LoginRequired
    @ApiOperation(value = "返回用户的所有收藏的内容", notes = "尚未测试")
    @GetMapping("/collectInfo/{userId}/{type}") // TODO 测试的时候不传参试试
    public ObjectResult<String> userCollect(@PathVariable int userId, @PathVariable(required = false) Integer type) {
        if(type == null) {
            type = 0;
        }
        countService.userCollect(userId, type);
        return ObjectResult.success("用户取消收藏成功");
    }

    /** 浏览量相关 **/

    @ApiOperation(value = "某实体类的浏览数量", notes = "尚未测试")
    @PostMapping("/browseCount")
    public ObjectResult<Integer> getBrowseCount(@RequestBody EntityBrowseCountDTO entityBrowseCountDTO) {
        Integer entityBrowseCount = countService.findEntityBrowseCount(entityBrowseCountDTO);
        return ObjectResult.success(entityBrowseCount);
    }

    // TODO 游客浏览状态下也可以加一
    @ApiOperation(value = "对该实体类的浏览量加一（说明：不与用户产生绑定）", notes = "尚未测试")
    @PostMapping("/browseAdd")
    public ObjectResult<String> addBrowseCount(@RequestBody EntityAddBrowseDTO entityAddBrowseDTO) {
        countService.addBrowseCount(entityAddBrowseDTO);
        return ObjectResult.success("浏览量加一成功");
    }

    /** 专栏相关 **/
    @LoginRequired
    @ApiOperation(value = "用户关注专栏", notes = "尚未测试")
    @PostMapping("/followColumn") // TODO 测试的时候不传参试试
    public ObjectResult<String> followColumn(FollowColumnDTO followColumnDTO) {
        countService.userFollowColumn(followColumnDTO);
        return ObjectResult.success("用户关注专栏成功");
    }

    @LoginRequired
    @ApiOperation(value = "用户取消关注专栏", notes = "尚未测试")
    @PostMapping("/unfollowColumn") // TODO 测试的时候不传参试试
    public ObjectResult<String> unfollowColumn(FollowColumnDTO followColumnDTO) {
        countService.userUnfollowColumn(followColumnDTO);
        return ObjectResult.success("用户取消关注专栏成功");
    }

    @LoginRequired
    @ApiOperation(value = "获取到用户和专栏关注与否的对应关系", notes = "尚未测试")
    @GetMapping("/collect111/{userId}") // TODO 测试的时候不传参试试
    public ObjectResult<Boolean> userFollowColumnState(FollowColumnDTO followColumnDTO) {
        Boolean res = countService.userFollowColumnState(followColumnDTO);
        return ObjectResult.success(res);
    }

}
