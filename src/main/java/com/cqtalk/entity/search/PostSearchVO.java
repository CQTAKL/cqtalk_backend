package com.cqtalk.entity.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchVO {

    private List<PostSearchInfo> postSearchInfoList;

    private Integer count;

}
