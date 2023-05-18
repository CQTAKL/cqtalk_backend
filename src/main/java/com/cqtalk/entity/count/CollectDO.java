package com.cqtalk.entity.count;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectDO {

    private Integer id;
    // 目前收藏仅支持帖子和文件
    private Integer type;

    private Long entityId;

    private Integer userId;

    private String labelName;

    private Date createTime;

}
