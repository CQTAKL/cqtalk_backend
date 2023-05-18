package com.cqtalk.entity.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddPostDTO {

    private Integer userId;

    private String title;

    private String briefIntro;

    private String content;

    private Long columnId;

    private Integer type;

}
