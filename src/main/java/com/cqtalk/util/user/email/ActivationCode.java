package com.cqtalk.util.user.email;

import com.cqtalk.util.redis.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class ActivationCode {

    @Autowired
    private RedisTemplate redisTemplate;

    // 随机生成一个六位数的验证码存入redis中，有效时长为5分钟。
    // 如果超过五分钟的话则重新生成验证码
    public void getActivationCode(String email) {
        Random random = new Random();
        int code = random.nextInt(899999) + 100000; // 100000 -  999999
        String redisKey = RedisKeyUtil.getActivationKey(email);
        System.out.println(redisKey);
        // 通过邮箱查找对应的验证码
        redisTemplate.opsForValue().set(redisKey, code, 300, TimeUnit.SECONDS);
    }

    /**
     * 检验验证码是否过期
     * @param email 传入的邮箱
     * @return true: 已过期；false: 未过期。
     */
    public boolean expiredActivationCode(String email) {
        String redisKey = RedisKeyUtil.getActivationKey(email);
        return redisTemplate.opsForValue().get(redisKey) == null;
    }

    public boolean testActivationCode(String email, String code) {
        String redisKey = RedisKeyUtil.getActivationKey(email);
        if(code.equals(redisTemplate.opsForValue().get(redisKey))) {
            return true;
        }
        return false;
    }

}
