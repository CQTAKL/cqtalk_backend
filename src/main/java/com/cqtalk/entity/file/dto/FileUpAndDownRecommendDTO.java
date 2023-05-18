package com.cqtalk.entity.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileUpAndDownRecommendDTO {

    @NotNull(message = "被推荐文件id不能为空")
    private Long fileId;

    @NotNull(message = "推荐的文件id不能为空")
    private Long recommendFileId;

    @NotNull(message = "推荐的位置信息不能为空")
    private Integer sortOrder;

}
