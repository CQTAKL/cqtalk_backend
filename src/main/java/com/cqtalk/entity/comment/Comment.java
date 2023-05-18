package com.cqtalk.entity.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private Long id;

    private Integer userId;

    private String content;

    private Integer locationId;

    private Long parentId;

    private Long atId;

    private Integer type;

    private Long entityId;

    private Integer state;

    private Integer likeCount;

    private Date createTime;

}
