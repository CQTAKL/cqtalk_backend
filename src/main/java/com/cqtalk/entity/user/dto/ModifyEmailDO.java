package com.cqtalk.entity.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyEmailDO {

    @NotNull(message = "邮箱不允许为空")
    private String email;

    @NotNull(message = "id不允许为空")
    private Integer id;

}
