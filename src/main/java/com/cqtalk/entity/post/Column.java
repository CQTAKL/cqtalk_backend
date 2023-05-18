package com.cqtalk.entity.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Column {

    private Integer id;

    private String title;

    private String description;

    private String content;

    private Date createTime;

    private Integer top;

    private Integer likeCount;

}
