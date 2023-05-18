package com.cqtalk.entity.count;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectInfoVO {

    private Integer entityType;

    private Long entityId;

    private String title;

    private String content;

    private String label;

    private Integer authUserId;

    private String nickName;

    private String realName;

    private Date createTime;

}
