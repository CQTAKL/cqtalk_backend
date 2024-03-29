package com.cqtalk.entity.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetCommentVO {

    private Integer type;

    private Long entityId;

    private List<CommentDataDTO> commentInfos;

}
