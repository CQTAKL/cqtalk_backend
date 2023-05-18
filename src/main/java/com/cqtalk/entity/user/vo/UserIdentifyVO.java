package com.cqtalk.entity.user.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIdentifyVO {

    // 是否是真的实名认证用户：true:真的；false:假的
    public Boolean trueIdentify;

    // 响应的信息
    public String responseInfo;

}
