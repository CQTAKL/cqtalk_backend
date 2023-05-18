package com.cqtalk.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyEmailDTO {

    @NotNull(message = "邮箱不允许为空")
    private String email;

    @NotNull(message = "验证码不允许为空")
    private Integer code;

}
