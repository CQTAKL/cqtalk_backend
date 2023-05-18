package com.cqtalk.entity.count.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectFileVO {

    private Long id;

    private String name;

    private String content;

    private Integer userId;

}
