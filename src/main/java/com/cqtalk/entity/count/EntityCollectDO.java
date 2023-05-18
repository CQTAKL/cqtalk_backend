package com.cqtalk.entity.count;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityCollectDO {

    private Integer type;

    private Long entityId;

    private Integer userId;

    private String labelName;

    private Date createTime;

}
