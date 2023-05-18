package com.cqtalk.entity.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDownloadDTO {

    @NotNull(message = "文件访问路径不允许为空")
    private Long fileId;

}
