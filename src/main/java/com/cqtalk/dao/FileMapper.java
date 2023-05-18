package com.cqtalk.dao;

import com.cqtalk.entity.count.CollectFileVO;
import com.cqtalk.entity.file.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {

    // file_type表格
    Integer selectSupportBySuffix(String suffix);

    Integer selectIdBySuffix(String suffix);

    Integer selectSupportById(Integer id);

    // file表格
    List<Long> getIdByFileUrl(String fileUrl);

    String selectFileNameById(Long id);

    void insertFile(File file);

    Integer selectCount();

    List<String> selectMd5();

    String selectPathByMd5(String md5);

    String selectPathById(Long id);

    void insertFileRecommendInfo(FileAddRecommendDTO fileAddRecommendDTO);

    List<Integer> selectShowOrdersByFileId(Long fileId);

    Integer selectIdByFileId(Long fileId);

    List<Long> selectRecommendFileIdByFileId(Long fileId);

    void deleteRecommendInfo(FileDeleteRecommendDTO fileDeleteRecommendDTO);

    void updateRecommendInfo(FileUpdateRecommendDO fileUpdateRecommendDO);

    List<FileRecommendResVO> getFileCommendInfoByFileId(Long fileId);

    Integer selectUserIdById(Long id);

    void updateStatusById(Long id);

    Integer selectIdByFileIdAndSortOrder(Long fileId, Integer sortOrder);

    Integer selectCountByFileId(Long fileId);

    Integer selectUserIdByFileId(Long fileId);

    void upRecommendSortOrder(FileUpdateRecommendDO fileUpdateRecommendDO);

    void downRecommendSortOrder(FileUpdateRecommendDO fileUpdateRecommendDO);

    CollectFileVO selectFileInfoById(Long id);

    FileTitleContentVO selectTitleAndContentById(Long id);


}
