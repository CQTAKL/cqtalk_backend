package com.cqtalk.entity.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileAddRecommendDTO {

    @NotNull(message = "被推荐的文件id不能为空")
    private Long fileId;
    @NotNull(message = "推荐的文件id不能为空")
    private Long recommendFileId;
    @NotNull(message = "推荐id的排序")
    private Integer sortOrder;

}
