package com.cqtalk.entity.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCount {

    private Integer comment;

    private Integer like;

    private Integer dislike;

    private Integer forward;

    private Integer collect;

    private Integer report;

    private Integer shield;

    private Integer browse;

}
