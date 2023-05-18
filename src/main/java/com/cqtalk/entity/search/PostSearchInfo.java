package com.cqtalk.entity.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "post")
public class PostSearchInfo {

    // entityType和type的区别请见开发文档
    @Field(type = FieldType.Integer)
    private Integer entityType;
    @Id
    private Long entityId;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String title;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String briefIntro;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String content;
    @Field(type = FieldType.Integer)
    private Integer type;

    public PostSearchInfo(PostSearchInfo source) {
        this.type = source.getType();
        this.entityId = source.getEntityId();
        this.entityType = source.getEntityType();
        this.title = source.getTitle();
        this.briefIntro = source.getBriefIntro();
        this.content = source.getContent();
    }
}
