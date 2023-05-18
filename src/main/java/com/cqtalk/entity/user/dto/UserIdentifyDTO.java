package com.cqtalk.entity.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIdentifyDTO {

    @NotNull(message = "姓名不能为空")
    private String realName;

    @NotNull(message = "身份证号不能为空")
    private String identifyCode;

}
