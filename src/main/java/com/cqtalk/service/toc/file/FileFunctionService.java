package com.cqtalk.service.toc.file;

import com.cqtalk.entity.file.dto.*;
import com.cqtalk.entity.file.vo.FileDownloadVO;
import com.cqtalk.entity.file.vo.FileRecommendVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileFunctionService {

    /**
     * 文件上传至服务器
     */
    String fileUpload(MultipartFile multipartFile, FileUploadDTO fileUploadDTO) throws Exception;

    /**
     * 文件删除
     */
    void deleteFile(FileDeleteDTO fileDeleteDTO);

    /**
     * 文件下载
     */
    FileDownloadVO fileDownload(FileDownloadDTO fileDownloadDTO);

    /**
     * 获取文件的推荐文件信息
     */
    FileRecommendVO getRecommendInfo(FileRecommendDTO fileUpdateRecommendDO);

    /**
     * 新增文件的推荐文件
     */
    void addRecommendFileInfo(FileAddRecommendDTO fileAddRecommendDTO);

    /**
     * 删除文件的推荐文件
     */
    void deleteRecommendFileInfo(FileDeleteRecommendDTO fileDeleteRecommendDTO);

    /**
     * 上传图片
     */
    String uploadPicture(MultipartFile multipartFile, FileUploadDTO fileUploadDTO) throws Exception;

    /**
     * 向上移
     */
    void upRecommendFileInfo(FileUpAndDownRecommendDTO fileUpAndDownRecommendDTO);

    /**
     * 向下移
     */
    void downRecommendFileInfo(FileUpAndDownRecommendDTO fileUpAndDownRecommendDTO);

}
