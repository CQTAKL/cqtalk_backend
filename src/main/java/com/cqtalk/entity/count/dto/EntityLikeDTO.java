package com.cqtalk.entity.count.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityLikeDTO {

    // 要点赞的类型 具体见ENTITY_TYPE_ENUM
    private Integer entityType;
    // 要点赞的id号
    private Long entityId;
    // 点赞的用户
    private Integer userId;

}
