package com.cqtalk.entity.count.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityBrowseCountDTO {

    // 要点赞的类型
    private Integer entityType;
    // 要点赞的id号
    private Long entityId;

}
