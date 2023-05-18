package com.cqtalk.entity.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentDTO {

    private Integer type;

    private Long entityId;

}
