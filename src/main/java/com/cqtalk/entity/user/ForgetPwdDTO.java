package com.cqtalk.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgetPwdDTO {

    private String email;

    private String newPwd;
    // 邮箱验证码
    private Integer code;

}
