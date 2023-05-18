package com.cqtalk.service.toc.user;

import com.cqtalk.entity.user.dto.*;
import com.cqtalk.entity.user.vo.UserShowInfoVO1;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface UserInfoService {

    /**
     * 修改个人展示信息
     */
    void modifyShowInfo(ModifyShowInfoDTO modifyShowInfoDTO);

    /**
     * 获取用户的展示信息
     */
    UserShowInfoVO1 getUserShowInfo(Integer id);

    /**
     * 修改密码
     */
    void modifyPassword(ModifyPasswordDTO modifyPasswordDTO);

    /**
     * 获取用户的邮箱
     */
    void getEmailCode(EmailCodeGetDTO emailCodeGetDTO);

    /**
     * 修改邮箱
     */
    void modifyEmail(ModifyEmailDTO modifyEmailDTO);

    /**
     * 恢复到上一个路径
     */
    void recoverLastUrl();

    /**
     * 修改用户头像
     */
    void modifyHeaderUrl(MultipartFile file) throws IOException;

    /**
     * 修改专业信息
     */
    void modifyMajor(ModifyMajorDTO modifyMajorDTO);

    /**
     * 用户实名认证
     */
    void userAuth(UserIdentifyDTO userIdentifyDTO);

    String getHeaderUrl(Integer userId);

    void followUser(UserFollowDTO userFollowDTO);

    void unfollowUser(UserUnfollowDTO userUnfollowDTO);

    Integer followState(UserFollowDTO userFollowDTO);

}
