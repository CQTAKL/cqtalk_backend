package com.cqtalk.entity.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnNoticeVO {

    private Long columnId;

    private String columnName;

    private Long postId;

    private String postName;

    private Integer userId;

    private String realName;

    private String nickName;

    private Integer state;

}
