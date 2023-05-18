package com.cqtalk.entity.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel("用户实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("真实姓名")
    private String realName;
    @ApiModelProperty("是否展示真实姓名")
    private Boolean showRealName;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("盐")
    private String salt;
    @ApiModelProperty("电话号码")
    private String phone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("用户ip地址")
    private String userIp;
    @ApiModelProperty("身份证号")
    private String identifyCode;
    @ApiModelProperty("状态")
    private Integer status;
    @ApiModelProperty("用户简介")
    private String briefIntro;
    @ApiModelProperty("qq号")
    private String qq;
    @ApiModelProperty("是不是vip, 0不是, 1是")
    private Integer vip;
    @ApiModelProperty("总共有的赞数")
    private Integer totalLikeNum;
    @ApiModelProperty("关注的人数")
    private Integer followCount;
    @ApiModelProperty("被关注的人数")
    private Integer followedCount;
    @ApiModelProperty("用户账号创建时间")
    private Date createTime;
    @ApiModelProperty("用户头像url，存储路径")
    private String headerUrl;
    @ApiModelProperty("用户上一张的头像url，存储路径")
    private String lastHeaderUrl;
    @ApiModelProperty("用户所在学校")
    private Integer school;
    @ApiModelProperty("用户专业")
    private Integer major;
    @ApiModelProperty("要考研的学校")
    private String graduateSchool;
    @ApiModelProperty("要考研的专业")
    private Integer graduateMajor;
    @ApiModelProperty("性别。1男，0女")
    private Integer gender;
    @ApiModelProperty("用户生日")
    private Date birth;
    @ApiModelProperty("个性签名")
    private String motto;
    @ApiModelProperty("账号，随机唯一字符串")
    private String accountNum;
    /*@ApiModelProperty("类型，即用户的身份，可以有多个身份")
    private Integer type;*/

}
