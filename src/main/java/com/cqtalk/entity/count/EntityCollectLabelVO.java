package com.cqtalk.entity.count;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityCollectLabelVO {

    private Integer userId;

    private List<String> labels;

}
