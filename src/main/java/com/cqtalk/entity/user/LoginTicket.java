package com.cqtalk.entity.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@ApiModel("登录凭证类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginTicket implements Serializable {

    @ApiModelProperty("登录凭证标识id")
    private int id;
    @ApiModelProperty("关联用户的id")
    private int userId;
    @ApiModelProperty("登录凭证")
    private String ticket;
    // TODO: 注：我们在开发的时候就在redis里设置好每个LoginTicket的登录过期时间，
    // 一般为cookie里过期时间加一个小时。不过状态这个字段还是要留着的
    @ApiModelProperty("登录状态。0：未登录，1：已登录")
    private int status;
    @ApiModelProperty("过期时间")
    private Date expired;

}
