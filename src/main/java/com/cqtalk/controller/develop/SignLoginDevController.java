package com.cqtalk.controller.develop;

import com.cqtalk.entity.user.RegisterDTO;
import com.cqtalk.service.develop.user.SignLoginDevService;
import com.cqtalk.util.returnObject.ObjectResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("性能测试::用户登录注册时调用的接口")
@RestController
@RequestMapping("/develop/user")
public class SignLoginDevController {

    private static final Logger logger = LoggerFactory.getLogger(SignLoginDevController.class);

    @Autowired
    private SignLoginDevService signLoginDevService;

    @ApiOperation(value = "处理注册操作", notes = "测试完成")
    @PostMapping("/register")
    public ObjectResult<String> register(@RequestBody RegisterDTO registerDTO) {
        signLoginDevService.register(registerDTO);
        logger.info("邮箱为::" + registerDTO.getEmail() + "::的用户注册成功");
        return ObjectResult.success("注册成功");
    }


}
