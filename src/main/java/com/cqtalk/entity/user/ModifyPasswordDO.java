package com.cqtalk.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModifyPasswordDO {

    @NotNull(message = "id不能为空")
    private Integer id;
    @NotNull(message = "密码不能为空")
    private String password;
}
