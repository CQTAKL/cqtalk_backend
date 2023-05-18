package com.cqtalk.entity.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {

    private Long id;

    @ApiModelProperty("文件名，不含后缀")
    private String name;

    @ApiModelProperty("文件后缀，比如：txt等等")
    private String suffix;

    private Integer typeId;

    private Integer size;

    @ApiModelProperty("文件大小的后缀，比如：B，KB，MB等等")
    private String sizeSuffix;

    private Date createTime;

    private Integer userId;

    private String path;

    private String path2;

    private String md5;

    private Integer comeFromId;



}
