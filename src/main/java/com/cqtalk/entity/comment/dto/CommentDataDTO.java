package com.cqtalk.entity.comment.dto;

import com.cqtalk.entity.comment.vo.CommentUserVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDataDTO {

    private Long id;

    private Integer userId;

    private CommentUserVO commentUserVO;

    private String content;

    private Integer locationId;

    private Integer likeCount;

    private Date createTime;

    private Long parentId;

    private Long atId;

}
