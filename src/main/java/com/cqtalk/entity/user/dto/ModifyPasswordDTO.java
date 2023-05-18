package com.cqtalk.entity.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPasswordDTO {

    @NotNull(message = "密码不能为空")
    private String password;

}
