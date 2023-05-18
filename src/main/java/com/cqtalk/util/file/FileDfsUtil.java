package com.cqtalk.util.file;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class FileDfsUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileDfsUtil.class);

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private FileTypeUtil fileTypeUtil;

    @Autowired
    private FileSizeUtil fileSizeUtil;

    /**
     * 上传文件
     * @param multipartFile 上传的文件
     * @return 返回此文件在服务器上存储的路径
     * @throws Exception
     */
    public String upload(MultipartFile multipartFile, Boolean picture) throws IOException {
        // 获取文件的后缀名
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        Long size = multipartFile.getSize();
        if(!picture) {
            fileTypeUtil.checkFileType(suffix);
        } else {
            fileTypeUtil.checkPictureType(suffix);
        }
        fileSizeUtil.checkSize(size);
        StorePath path;
        if(picture) {
            path = this.storageClient.uploadImageAndCrtThumbImage(
                    multipartFile.getInputStream(), size, suffix, null);
        } else {
            path = storageClient.uploadFile("group1", multipartFile.getInputStream(),
                    size, suffix);
        }
        System.out.println("full path: " + path.getFullPath());
        System.out.println("path: " + path.getPath());
        return path.getFullPath();
        /*StorePath storePath;
        try {
            storePath = this.storageClient.uploadImageAndCrtThumbImage(
                    multipartFile.getInputStream(), multipartFile.getSize(), suffix, null);
        } catch(Exception e) {
            e.printStackTrace();
            throw new DescribeException(ErrorCode.SERVER_FAIL.getCode(), ErrorCode.SERVER_FAIL.getMsg());
        }*/
        // 返回此文件在服务器上存储的路径
    }

    /**
     * 上传图片
     * @param multipartFile
     * @return
     */
    public String uploadPicture(MultipartFile multipartFile) throws IOException {
        // 获取文件的后缀名
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        Long size = multipartFile.getSize();

        fileSizeUtil.checkSize(size);
        StorePath storePath;
        storePath = this.storageClient.uploadImageAndCrtThumbImage(
                    multipartFile.getInputStream(), multipartFile.getSize(), suffix, null);
        return storePath.getFullPath();
    }

    /**
     * 删除文件
     * @param fileUrl 要删除的文件在服务器中存储的路径
     */
    public void deleteFile(String fileUrl) {
        if(StringUtils.isEmpty(fileUrl)) {
            logger.info("用户输入的文件路径为空");
            return;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }

    /**
     * 文件下载
     */
    public byte[] download(String fileUrl) {
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        byte[] bytes = storageClient.downloadFile(group, path, new DownloadByteArray());
        return bytes;
    }

}
