package com.cqtalk.entity.post.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostVO {
    // TODO
    private Long id;

    private PostUserVO postUserVO;

    private String title;

    private String content;

    private Date createTime;

    private Integer format;

    private Integer locationId;




}
