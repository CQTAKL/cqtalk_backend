package com.cqtalk.entity.count.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityCollectStateDTO {

    // 收藏的类型 具体见ENTITY_TYPE_ENUM
    private Integer entityType;
    // 收藏的id号
    private Long entityId;
    // 收藏的用户
    private Integer userId;

}
