package com.cqtalk.entity.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    private String topic;

    private Integer userId;

    private Integer entityId;

    private Long entityType;
    // 实体类发布者
    private Integer entityUserId;


}
