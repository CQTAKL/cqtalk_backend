package com.cqtalk.entity.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCommentDTO {

    private String content;

    private Long parentId;

    private Long atId;

    private Integer type;

    private Long entityId;

}
