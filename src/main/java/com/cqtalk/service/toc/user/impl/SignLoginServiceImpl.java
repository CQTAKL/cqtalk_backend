package com.cqtalk.service.toc.user.impl;

import com.cqtalk.dao.UserMapper;
import com.cqtalk.entity.user.*;
import com.cqtalk.enumerate.email.EMAIL_SEND_TYPE;
import com.cqtalk.enumerate.user.LOGIN_STATUS_ENUM;
import com.cqtalk.enumerate.user.REMEMBER_EXPIRED_ENUM;
import com.cqtalk.enumerate.user.USER_STATE_ENUM;
import com.cqtalk.service.toc.user.SignLoginService;
import com.cqtalk.util.encrypt.MD5Util;
import com.cqtalk.util.encrypt.UUIDUtil;
import com.cqtalk.util.jwt.JWTUtil;
import com.cqtalk.util.redis.RedisKeyUtil;
import com.cqtalk.util.returnObject.DescribeException;
import com.cqtalk.util.returnObject.ErrorCode;
import com.cqtalk.util.user.email.EmailActivationUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class SignLoginServiceImpl implements SignLoginService {

    private static final Logger logger = LoggerFactory.getLogger(SignLoginServiceImpl.class);

    @Autowired
    private EmailActivationUtil emailActivationUtil;

    @Autowired
    private UserMapper userMapper;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 获取邮箱验证码
     *
     * @param emailCodeGetDTO
     */
    @Override
    public void emailActivation(EmailCodeGetDTO emailCodeGetDTO) {
        String email = emailCodeGetDTO.getEmail();
        // 如果这个邮箱已经存在的话直接返回错误（证明这个邮箱已经被注册过了）
        Integer id = userMapper.selectIdByEmail(email);
        if(id != null) {
            throw new DescribeException(ErrorCode.EMAIL_ACTIVATION_USED_EMAIL.getCode(), ErrorCode.EMAIL_ACTIVATION_USED_EMAIL.getMsg());
        }
        emailActivationUtil.getEmailCode(email, EMAIL_SEND_TYPE.MODIFY.getType());
    }

    /**
     * 用户注册
     *
     * @param registerDTO
     */
    @Override
    public void register(RegisterDTO registerDTO) {
        emailActivationUtil.verifyEmailCode(registerDTO.getEmail(), registerDTO.getCode());
        Integer uid = userMapper.selectIdByEmail(registerDTO.getEmail());
        if(uid != null) {
            throw new DescribeException(ErrorCode.REGISTER_USED_EMAIL_ERROR.getCode(), ErrorCode.REGISTER_USED_EMAIL_ERROR.getMsg());
        }
        User user = new User();
        user.setNickName("用户");
        user.setEmail(registerDTO.getEmail());
        // 密码采用MD5方式加密
        user.setSalt(UUIDUtil.generateUUID().substring(0, 5));
        user.setPassword(MD5Util.md5(registerDTO.getPassword() + user.getSalt()));
        // user.setType(USER_TYPE_ENUM.GENERAL.getType()); // 通过注册页面注册的用户默认为普通用户
        user.setStatus(USER_STATE_ENUM.NORMAL.getType()); // 默认已经激活账号
        // 目前是借助牛客网的头像图库（其实是来源于github）
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);
    }

    /**
     * 用户登录
     * @param loginDTO
     */
    @Override
    public LoginResultDTO login(LoginDTO loginDTO, String captchaOwner) {
        if(captchaOwner.equals("-1")) {
            throw new DescribeException(ErrorCode.LOGIN_NO_CAPTCHA_ERROR.getCode(), ErrorCode.LOGIN_NO_CAPTCHA_ERROR.getMsg());
        }
        String captcha = (String) redisTemplate.opsForValue().get(captchaOwner);
        // 校验验证码是否正确
        captchaVerify(false, loginDTO.getVerificationCode(), captcha);
        boolean remember = loginDTO.getRememberMe();
        int expiredSeconds = remember ? REMEMBER_EXPIRED_ENUM.REMEMBER_EXPIRED_SECONDS.getType() : REMEMBER_EXPIRED_ENUM.DEFAULT_EXPIRED_SECONDS.getType();
        int type = loginDTO.getTextId();
        String utext = loginDTO.getUserText();
        Integer id;
        if(type == 1) {
            throw new DescribeException(ErrorCode.LOGIN_NO_FUNCTION_ERROR.getCode(), ErrorCode.LOGIN_NO_FUNCTION_ERROR.getMsg());
            // id = userMapper.selectIdByPhone(utext);
        } else if(type == 2) {
            id = userMapper.selectIdByEmail(utext);
        } else if(type == 3) {
            throw new DescribeException(ErrorCode.LOGIN_NO_FUNCTION_ERROR.getCode(), ErrorCode.LOGIN_NO_FUNCTION_ERROR.getMsg());
            // id = userMapper.selectIdByRealName(utext);
        } else {
            throw new DescribeException(ErrorCode.LOGIN_INPUT_TEXT_ERROR.getCode(), ErrorCode.LOGIN_NO_USER_ERROR.getMsg());
        }
        if(id == null) {
            throw new DescribeException(ErrorCode.LOGIN_NO_USER_ERROR.getCode(), ErrorCode.LOGIN_NO_USER_ERROR.getMsg());
        }
        String password = loginDTO.getPassword();
        String salt = userMapper.selectSaltById(id);
        String userPassword = userMapper.selectPasswordById(id);
        // 验证密码
        password = MD5Util.md5(password + salt);
        if(!userPassword.equals(password)) {
            throw new DescribeException(ErrorCode.LOGIN_PASSWORD_ERROR.getCode(), ErrorCode.LOGIN_PASSWORD_ERROR.getMsg());
        }
        // 生成登录凭证，存到redis里
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(id);
        String loginTicketValue = UUIDUtil.generateUUID();
        loginTicket.setTicket(loginTicketValue);
        loginTicket.setStatus(LOGIN_STATUS_ENUM.LOGIN_SUCCESS.getType());
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000L));
        String redisKey = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
        redisTemplate.opsForValue().set(redisKey, loginTicket, expiredSeconds * 1000L, TimeUnit.SECONDS);
        Cookie cookie = new Cookie("loginTicket", loginTicket.getTicket());
        cookie.setPath("/"); // TODO 此处可以详细了解，并产出一篇文章
        cookie.setMaxAge(expiredSeconds);
        LoginResultDTO loginResultDTO = new LoginResultDTO();
        loginResultDTO.setUserId(id);
        loginResultDTO.setLoginTicket(loginTicketValue);
        loginResultDTO.setExpiredTime(expiredSeconds);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("loginTicket", loginTicketValue);
        String token = JWTUtil.createToken(map);
        loginResultDTO.setJwtToken(token);
        return loginResultDTO;
    }

    /**
     * 图形验证码校验
     *
     * @param inspection       是否进行图形验证码校验：true:是，false:否
     * @param verificationCode 用户输入的图形验证码
     * @param kaptchaOwner     用户浏览器里的cookie里的校验验证码的信息
     */
    @Override
    public void captchaVerify(Boolean inspection, String verificationCode, String kaptchaOwner) {
        if(inspection) {
            // 1. 判断验证码正确与否
            String captcha;
            if(StringUtils.isNotBlank(kaptchaOwner)) {
                String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
                captcha = (String) redisTemplate.opsForValue().get(redisKey);
            } else {
                throw new DescribeException(ErrorCode.VERIFY_NO_CAPTCHA_CODE_ERROR.getCode(), ErrorCode.VERIFY_NO_CAPTCHA_CODE_ERROR.getMsg());
            }
            // 后台的验证码为空 或 用户传入的验证码为空 或 忽略大小写后的值不等
            if(StringUtils.isBlank(captcha) || StringUtils.isBlank(verificationCode) || !captcha.equalsIgnoreCase(verificationCode)) {
                throw new DescribeException(ErrorCode.LOGIN_CAPTCHA_ERROR.getCode(), ErrorCode.LOGIN_CAPTCHA_ERROR.getMsg());
            }
        }
    }

    /**
     * 用户登出
     *
     * @param ticket
     */
    @Override
    public void logout(String ticket) {
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(redisKey);
        loginTicket.setStatus(LOGIN_STATUS_ENUM.LOGOUT_SUCCESS.getType());
        // 重新修改redis里的数据状态
        redisTemplate.opsForValue().set(redisKey, loginTicket);
    }

    /**
     * 通过cookie里的ticket找到登录凭证信息
     *
     * @param ticket
     */
    @Override
    public LoginTicket findLoginTicket(String ticket) {
        String redisKey = RedisKeyUtil.getTicketKey(ticket);
        return (LoginTicket) redisTemplate.opsForValue().get(redisKey);
    }

    /**
     * 忘记密码后的找回操作
     * @param forgetPwdDTO
     */
    @Override
    public void forgetPwd(ForgetPwdDTO forgetPwdDTO) {
        Integer code = forgetPwdDTO.getCode();
        String email = forgetPwdDTO.getEmail();
        emailActivationUtil.verifyEmailCode(email, code);
        Integer id = userMapper.selectIdByEmail(email);
        String newPwd = forgetPwdDTO.getNewPwd();
        ModifyPasswordDO modifyPasswordDO = new ModifyPasswordDO();
        modifyPasswordDO.setId(id);
        modifyPasswordDO.setPassword(newPwd);
        userMapper.updatePasswordById(modifyPasswordDO);
    }


}
