package com.cqtalk.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserShowInfoVO1 {

    private String nickName;

    private String birth;

    private String motto;

    private String briefIntro;

}
