package com.cqtalk.entity.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private Long id;
    private Integer userId;
    private String title;
    private String briefIntro;
    private String content;
    private Date createTime;
    // 格式。1：markdown格式文档；2：富文本格式文档
    private Integer format;
    private Integer locationId;
    // 所属专栏，如果不属于任何专栏则值为-1
    private Long columnId;
    private Integer state;

    public Post(Post post) {
        this.id = post.getId();
        this.userId = post.getUserId();
        this.title = post.getTitle();
        this.briefIntro = post.getBriefIntro();
        this.content = post.getContent();
        this.createTime = post.getCreateTime();
        this.format = post.getFormat();
        this.locationId = post.getLocationId();
        this.columnId = post.getColumnId();
        this.state = post.getState();
    }

    // 以下是数量相关


}
