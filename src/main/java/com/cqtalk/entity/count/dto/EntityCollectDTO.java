package com.cqtalk.entity.count.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityCollectDTO {

    private Integer type;

    private Long entityId;

    private String labelName;

}
