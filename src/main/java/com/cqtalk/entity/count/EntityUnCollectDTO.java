package com.cqtalk.entity.count;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityUnCollectDTO {

    // 取消收藏的类型 具体见ENTITY_TYPE_ENUM
    private Integer entityType;
    // 取消收藏的id号
    private Long entityId;
    // 取消收藏的用户信息
    private Integer userId;

}
