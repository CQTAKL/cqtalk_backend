package com.cqtalk.entity.file.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileRecommendVO {

    private Long fileId;

    private List<FileRecommendResVO> fileRecommendResVOList;

}
