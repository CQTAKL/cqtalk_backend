package com.cqtalk.service.toc.user;

import com.cqtalk.entity.user.*;
import org.springframework.stereotype.Service;

@Service
public interface SignLoginService {

    /**
     * 获取邮箱验证码
     */
    void emailActivation(EmailCodeGetDTO emailCodeGetDTO);

    /**
     * 用户注册
     */
    void register(RegisterDTO registerDTO);

    /**
     * 用户登录
     * @param loginDTO
     */
    LoginResultDTO login(LoginDTO loginDTO, String captchaOwner);

    /**
     * 图形验证码校验
     * @param inspection 是否进行图形验证码校验：true:是，false:否
     * @param verificationCode 用户输入的图形验证码
     * @param kaptchaOwner 用户浏览器里的cookie里的校验验证码的信息
     */
    void captchaVerify(Boolean inspection, String verificationCode, String kaptchaOwner);

    /**
     * 用户登出
     * @param ticket
     */
    void logout(String ticket);

    /**
     * 通过cookie里的ticket找到登录凭证信息
     */
    LoginTicket findLoginTicket(String ticket);

    void forgetPwd(ForgetPwdDTO forgetPwdDTO);

}
