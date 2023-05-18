package com.cqtalk.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIdentifyDO {

    @NotNull(message = "id不能为空")
    private Integer id;

    @NotNull(message = "姓名不能为空")
    private String realName;

    @NotNull(message = "身份证号不能为空")
    private String identifyCode;

}
