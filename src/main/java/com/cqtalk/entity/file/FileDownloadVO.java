package com.cqtalk.entity.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDownloadVO {

    @ApiModelProperty("返回文件名, 包括后缀")
    private String wholeName;

    @ApiModelProperty("下载文件的字节流形式")
    private byte[] bytes;

}
