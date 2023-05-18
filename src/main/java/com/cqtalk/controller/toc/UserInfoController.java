package com.cqtalk.controller.toc;

import com.cqtalk.annotation.LoginRequired;
import com.cqtalk.entity.user.*;
import com.cqtalk.service.toc.user.UserInfoService;
import com.cqtalk.util.returnObject.ObjectResult;
import com.cqtalk.util.user.info.HostHolder;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/info")
public class UserInfoController {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private HostHolder hostHolder;

    @LoginRequired
    @ApiOperation(value = "修改个人展示信息", notes = "测试完成")
    @PostMapping("/modifyShow")
    public ObjectResult<String> modifyUserInfo(@RequestBody ModifyShowInfoDTO modifyShowInfoDTO) {
        userInfoService.modifyShowInfo(modifyShowInfoDTO);
        return ObjectResult.success("修改成功。");
    }

    @LoginRequired
    @ApiOperation(value = "获取个人展示信息", notes = "尚未测试")
    @GetMapping("/show/{id}")
    public ObjectResult<UserShowInfoVO1> getUserShowInfo(@PathVariable("id") int id) {
        UserShowInfoVO1 userShowInfo1 = userInfoService.getUserShowInfo(id);
        return ObjectResult.success(userShowInfo1);
    }

    // TODO 此处修改逻辑
    @LoginRequired
    @ApiOperation(value = "修改密码", notes = "测试完成")
    @PostMapping("/modifyPwd")
    public ObjectResult<String> modifyPassword(@RequestBody ModifyPasswordDTO modifyPasswordDTO) {
        userInfoService.modifyPassword(modifyPasswordDTO);
        return ObjectResult.success("修改成功。");
    }

    @LoginRequired
    @ApiOperation(value = "用户修改绑定邮箱的信息时获取邮箱的验证码", notes = "尚未测试")
    @PostMapping("/emailCode")
    public ObjectResult<String> getEmailCode(@RequestBody EmailCodeGetDTO emailCodeGetDTO) {
        userInfoService.getEmailCode(emailCodeGetDTO);
        return ObjectResult.success("验证码已发送至您的邮箱，请注意查收。若没有收到请联系张创琦：qq: 1020238657");
    }

    @LoginRequired
    @ApiOperation(value = "修改邮箱", notes = "测试通过")
    @PostMapping("/modifyEmail")
    public ObjectResult<String> modifyEmail(@RequestBody ModifyEmailDTO modifyEmailDTO) {
        userInfoService.modifyEmail(modifyEmailDTO);
        return ObjectResult.success("修改成功。");
    }

    @LoginRequired
    @ApiOperation(value = "修改实名认证信息", notes = "尚未测试")
    @PostMapping("/auth")
    public ObjectResult<String> modifyAuth(@RequestBody UserIdentifyDTO userIdentifyDTO) {
        userInfoService.userAuth(userIdentifyDTO);
        return ObjectResult.success("实名认证成功。");
    }

    @LoginRequired
    @ApiOperation(value = "恢复上一张头像", notes = "尚未测试")
    @PostMapping("/lastHeaderUrl")
    public ObjectResult<String> recoverLastUrl() {
        userInfoService.recoverLastUrl();
        return ObjectResult.success("恢复成功。");
    }

    @LoginRequired
    @ApiOperation(value = "修改用户头像", notes = "尚未测试")
    @PostMapping("/modifyHeaderUrl")
    public ObjectResult<String> modifyHeaderUrl(@RequestParam("files") MultipartFile file) throws IOException {
        userInfoService.modifyHeaderUrl(file);
        return ObjectResult.success("修改成功。");
    }

    @LoginRequired
    @ApiOperation(value = "大学/专业/考研等信息修改", notes = "尚未测试")
    @PostMapping("/modifyMajor")
    public ObjectResult<String> modifyMajor(@RequestBody ModifyMajorDTO modifyMajorDTO) {
        userInfoService.modifyMajor(modifyMajorDTO);
        return ObjectResult.success("修改成功。");
    }

    @LoginRequired
    @ApiOperation(value = "获取用户的头像", notes = "尚未测试")
    @GetMapping("/headerUrl")
    public ObjectResult<String> getHeaderUrl() {
        Integer userId = hostHolder.getUserId();
        String headerUrl = userInfoService.getHeaderUrl(userId);
        return ObjectResult.success(headerUrl);
    }

    // 用户关注某人
    @LoginRequired
    @ApiOperation(value = "用户关注某人", notes = "尚未测试")
    @PostMapping("/userFollow")
    public ObjectResult<String> userFollow(@RequestBody UserFollowDTO userFollowDTO) {
        userInfoService.followUser(userFollowDTO);
        return ObjectResult.success("关注成功");
    }

    // 用户取消关注某人
    @LoginRequired
    @ApiOperation(value = "用户取消关注某人", notes = "尚未测试")
    @PostMapping("/userUnfollow")
    public ObjectResult<String> userUnfollow(@RequestBody UserUnfollowDTO userUnfollowDTO) {
        userInfoService.unfollowUser(userUnfollowDTO);
        return ObjectResult.success("取消关注成功");
    }

    @LoginRequired
    @ApiOperation(value = "用户关注某人", notes = "尚未测试")
    @PostMapping("/myFollow")
    public ObjectResult<String> myFollow(@RequestBody MyFollowDTO myFollowDTO) {
        UserFollowDTO userFollowDTO = new UserFollowDTO();
        userFollowDTO.setUserBeFollowId(myFollowDTO.getUserBeFollowId());
        userFollowDTO.setUserId(hostHolder.getUserId());
        userInfoService.followUser(userFollowDTO);
        return ObjectResult.success("关注成功");
    }

    // 用户取消关注某人
    @LoginRequired
    @ApiOperation(value = "用户取消关注某人", notes = "尚未测试")
    @PostMapping("/myUnfollow")
    public ObjectResult<String> myUnfollow(@RequestBody MyUnfollowDTO myUnfollowDTO) {
        UserUnfollowDTO userUnfollowDTO = new UserUnfollowDTO();
        userUnfollowDTO.setUserId(hostHolder.getUserId());
        userUnfollowDTO.setUserBeFollowId(myUnfollowDTO.getUserBeFollowId());
        userInfoService.unfollowUser(userUnfollowDTO);
        return ObjectResult.success("取消关注成功");
    }

    // 用户对需要查询用户的关注状态
    @LoginRequired
    @ApiOperation(value = "用户对需要查询用户的关注状态", notes = "尚未测试")
    @PostMapping("/followState")
    public ObjectResult<Integer> userFollowState(@RequestBody UserFollowDTO userFollowDTO) {
        Integer state = userInfoService.followState(userFollowDTO);
        return ObjectResult.success(state);
    }

}
