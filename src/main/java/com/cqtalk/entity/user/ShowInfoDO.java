package com.cqtalk.entity.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowInfoDO {

    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("用户生日")
    private Date birth;
    @ApiModelProperty("个性签名")
    private String motto;
    @ApiModelProperty("简介")
    private String briefIntro;

}
