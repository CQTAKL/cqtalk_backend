package com.cqtalk.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

// 获取邮箱的验证码
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailCodeGetDTO {

    @NotNull(message = "邮箱不能为空")
    private String email;

}
