package com.cqtalk.controller.toc;

import com.cqtalk.entity.user.dto.*;
import com.cqtalk.entity.user.vo.CaptchaVO;
import com.cqtalk.service.toc.user.SignLoginService;
import com.cqtalk.util.encrypt.UUIDUtil;
import com.cqtalk.util.redis.RedisKeyUtil;
import com.cqtalk.util.returnObject.ObjectResult;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;
import java.util.Base64.Encoder;

@Api("用户登录注册时调用的接口")
@RestController
@RequestMapping("/user")
public class SignLoginController {

    private static final Logger logger = LoggerFactory.getLogger(SignLoginController.class);

    @Autowired
    private SignLoginService signLoginService;

    @Autowired
    private Producer kaptchaProducer;

    @Resource
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "测试", notes = "测试完成")
    @PostMapping("/test1")
    public ObjectResult<String> test1() {
        return ObjectResult.success("测试成功");
    }

    @ApiOperation(value = "获取邮箱验证码", notes = "尚未测试")
    @PostMapping("/emailCode")
    public ObjectResult<String> emailActivation(@RequestBody EmailCodeGetDTO emailCodeGetDTO) {
        signLoginService.emailActivation(emailCodeGetDTO);
        return ObjectResult.success("验证码已发送至您的邮箱，请注意查收。若没有收到请联系张创琦：qq: 1020238657");
    }

    @ApiOperation(value = "处理注册操作", notes = "测试完成")
    @PostMapping("/register")
    public ObjectResult<String> register(@RequestBody RegisterDTO registerDTO) {
        signLoginService.register(registerDTO);
        return ObjectResult.success("注册成功");
    }

    @ApiOperation(value = "获取/刷新图形验证码", notes = "测试完成")
    @GetMapping("/captcha")
    public ObjectResult<CaptchaVO> getCaptcha(HttpServletResponse response) {
        // 生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);
        // 验证码的归属
        String kaptchaOwner = UUIDUtil.generateUUID();
        // 将验证码存入Redis
        String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
        redisTemplate.opsForValue().set(redisKey, text, 300, TimeUnit.SECONDS);
        // 将图片输出给浏览器
        response.setContentType("image/png");
        ByteArrayOutputStream baos = new ByteArrayOutputStream(); // io流
        try {
            ImageIO.write(image, "png", baos);//写入流中
        } catch (IOException e) {
            logger.error("图片验证码部分::响应验证码失败::" + e.getMessage());
        }
        byte[] bytes = baos.toByteArray(); // 转换成字节
        Encoder encoder = Base64.getEncoder();
        String result = encoder.encodeToString(bytes).trim();
        result = result.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptcha(result);
        captchaVO.setCaptchaOwner(kaptchaOwner);
        return ObjectResult.success(captchaVO);
    }

    // TODO cookie逻辑重新实现
    @ApiOperation(value = "用户登录", notes = "测试完成")
    @PostMapping("/login")
    public ObjectResult<LoginResultDTO> login(HttpServletRequest request, @RequestBody LoginDTO loginDTO) {
        String captchaOwner = request.getHeader("captchaOwner");
        String token = request.getHeader("Authorization");
        if(!token.equals("-1")) { // 用户已经登录
            return ObjectResult.error("400", "上一个用户尚未退出登录，请先退出登录");
        }
        LoginResultDTO res = signLoginService.login(loginDTO, captchaOwner);
        return ObjectResult.success(res);
    }

    @ApiOperation(value = "处理登出操作", notes = "测试完成")
    @PostMapping("/logout")
    public ObjectResult<String> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String ticket = null;
        if(token != null && !token.equals("-1")) {
            ticket = StringUtils.substringAfter(token, "_");
        } else {
            return ObjectResult.success("此帐号退出成功");
        }
        signLoginService.logout(ticket);
        return ObjectResult.success("此帐号退出成功");
    }

    @ApiOperation(value = "忘记密码", notes = "尚未测试")
    @PostMapping("/forgetPwd")
    public ObjectResult<String> forgetPwd(ForgetPwdDTO forgetPwdDTO) {
        signLoginService.forgetPwd(forgetPwdDTO);
        return ObjectResult.success("重置密码成功");
    }

}
