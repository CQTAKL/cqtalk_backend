package com.cqtalk.entity.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {


    private Integer textId;

    @NotNull(message = "此输入框不能为空")
    private String userText;

    @NotNull(message = "密码不能为空")
    private String password;

    @NotNull(message = "验证码不能为空")
    private String verificationCode;

    private Boolean rememberMe;




}
