package com.cqtalk.entity.count.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectPostVO {

    private Long id;

    private String title;

    private String content;

    private Integer userId;


}
