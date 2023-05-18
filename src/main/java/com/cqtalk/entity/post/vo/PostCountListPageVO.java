package com.cqtalk.entity.post.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCountListPageVO {

    private Long postId;

    private Integer browse;

    private Integer like;

    private Integer comment;

}
