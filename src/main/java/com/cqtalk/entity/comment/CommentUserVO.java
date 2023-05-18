package com.cqtalk.entity.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentUserVO {

    private String realName;

    private String nickName;

    private Boolean showRealName;
    @ApiModelProperty("用户简介")
    private String briefIntro;
    @ApiModelProperty("是不是vip, 0不是, 1是")
    private Integer vip;
    @ApiModelProperty("用户头像url，存储路径")
    private String headerUrl;

}
