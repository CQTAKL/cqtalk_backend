package com.cqtalk.entity.post.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostListSingleVO {

    private PostUserVO postUserVO;

    private Long id;

    private String title;

    private String briefIntro;

    private Integer format;

    private Long columnId;

    private Integer browseCount;

    private Integer likeCount;

    private Integer commentCount;

    private Date createTime;

    private Integer locationId;

}
