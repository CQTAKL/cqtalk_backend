package com.cqtalk.entity.queue.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemInfoSingleVO {

    private Integer userId;

    private String realName;

    private String nickName;

    private String createTime;

    private Integer entityType;

    private Long entityId;

    private String title;

}
