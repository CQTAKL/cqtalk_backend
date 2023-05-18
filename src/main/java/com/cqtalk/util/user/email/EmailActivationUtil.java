package com.cqtalk.util.user.email;

import com.cqtalk.dao.UserMapper;
import com.cqtalk.enumerate.email.EMAIL_SEND_TYPE;
import com.cqtalk.util.redis.RedisKeyUtil;
import com.cqtalk.util.returnObject.DescribeException;
import com.cqtalk.util.returnObject.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmailActivationUtil {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ActivationCode activationCode;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MailUtil mailUtil;

    /**
     * 验证邮箱验证码是否正确
     */
    public void verifyEmailCode(String email, Integer code) {
        String redisKey = RedisKeyUtil.getActivationKey(email);
        if(redisTemplate.opsForValue().get(redisKey) == null) {
            throw new DescribeException(ErrorCode.REGISTER_NOT_GET_CODE_ERROR.getCode(), ErrorCode.REGISTER_NOT_GET_CODE_ERROR.getMsg());
        }
        int userCode = (int) redisTemplate.opsForValue().get(redisKey);
        if(userCode != code) {
            throw new DescribeException(ErrorCode.REGISTER_CODE_ERROR.getCode(), ErrorCode.REGISTER_CODE_ERROR.getMsg());
        }
    }

    /**
     * 获取邮箱验证码
     *
     */
    public void getEmailCode(String email, int type) {
        // 如果验证码过期的话再次生成验证码
        if(activationCode.expiredActivationCode(email)) {
            activationCode.getActivationCode(email);
        }
        String redisKey = RedisKeyUtil.getActivationKey(email);
        int code = (int) redisTemplate.opsForValue().get(redisKey);
        String stringCode = "" + code;
        try {
            if(type == EMAIL_SEND_TYPE.REGISTER.getType()) {
                mailUtil.sendRegisterCode(email, stringCode);
            } else if(type == EMAIL_SEND_TYPE.MODIFY.getType()) {
                mailUtil.sendModifyCode(email, stringCode);
            }
        } catch (Exception e) {
            throw new DescribeException(ErrorCode.EMAIL_ACTIVATION_NO_EMAIL.getCode(), ErrorCode.EMAIL_ACTIVATION_NO_EMAIL.getMsg());
        }

    }

}
