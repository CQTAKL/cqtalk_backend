package com.cqtalk.entity.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    @NotNull(message = "email不允许为空")
    private String email;

    @NotNull(message = "密码不允许为空")
    private String password;

    @NotNull(message = "验证码不允许为空")
    private Integer code;


}
