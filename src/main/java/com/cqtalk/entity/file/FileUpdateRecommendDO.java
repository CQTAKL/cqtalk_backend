package com.cqtalk.entity.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUpdateRecommendDO {

    private Long fileId;

    private Integer sortOrder;

}
