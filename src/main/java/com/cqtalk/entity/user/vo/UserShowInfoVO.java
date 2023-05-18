package com.cqtalk.entity.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserShowInfoVO {

    private String nickName;

    private Date birth;

    private String motto;

    private String briefIntro;

}
