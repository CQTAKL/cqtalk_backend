package com.cqtalk.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

// 修改用户实名认证信息
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyUserAuthDTO {

    @NotNull(message = "姓名不能为空")
    private String realName;

    @NotNull(message = "身份证号不能为空")
    private String identifyCode;

}
