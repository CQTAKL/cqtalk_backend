package com.cqtalk.service.toc.file.impl;

import com.cqtalk.dao.FileMapper;
import com.cqtalk.entity.file.*;
import com.cqtalk.entity.file.dto.*;
import com.cqtalk.entity.file.vo.FileDownloadVO;
import com.cqtalk.entity.file.vo.FileRecommendResVO;
import com.cqtalk.entity.file.vo.FileRecommendVO;
import com.cqtalk.enumerate.file.FILE_RECOMMEND_ENUM;
import com.cqtalk.enumerate.file.FILE_TYPE_ENUM;
import com.cqtalk.service.toc.file.FileFunctionService;
import com.cqtalk.service.toc.file.FileService;
import com.cqtalk.util.encrypt.MD5Util;
import com.cqtalk.util.file.FileDfsUtil;
import com.cqtalk.util.file.FileDuplicateUtil;
import com.cqtalk.util.file.FileSizeUtil;
import com.cqtalk.util.file.FileTypeUtil;
import com.cqtalk.util.getid.SnowflakeIdWorker;
import com.cqtalk.util.returnObject.DescribeException;
import com.cqtalk.util.returnObject.ErrorCode;
import com.cqtalk.util.user.info.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Date;
import java.util.List;


@Service
public class FileFunctionServiceImpl implements FileFunctionService {

    @Autowired
    private FileDfsUtil fileDfsUtil;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private FileSizeUtil fileSizeUtil;

    @Autowired
    private FileDuplicateUtil fileDuplicateUtil;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileTypeUtil fileTypeUtil;

    /**
     * 文件上传至服务器
     */
    @Override
    public String fileUpload(MultipartFile multipartFile, FileUploadDTO fileUploadDTO) throws Exception {
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        fileTypeUtil.checkFileType(suffix);
        // 先判断服务器上有没有一样的文件
        byte[] bytes = multipartFile.getBytes();
        String md5 = MD5Util.getBytesMd5(bytes);
        boolean repeat = fileDuplicateUtil.duplicateCheck(md5);
        String filePath;
        if(repeat) {
            // 根据md5值找到对应的文件在linux上存储的路径
            filePath = fileMapper.selectPathByMd5(md5);
        } else {
            // 如果服务器中没有的话
            // ※※ 上传文件 ※※
            // full path: group1/M00/00/00/wKgqgWQTZ_2AMm0mAAAXHlMyylI996.pdf
            // path: M00/00/00/wKgqgWQTZ_2AMm0mAAAXHlMyylI996.pdf
            // 此处的filePath为：group1/M00/00/00/wKgqgWQTZ_2AMm0mAAAXHlMyylI996.pdf
            int support = fileMapper.selectSupportBySuffix(suffix);
            if(support == FILE_TYPE_ENUM.PICTURE.getType()) {
                filePath = fileDfsUtil.upload(multipartFile, true);
            } else {
                filePath = fileDfsUtil.upload(multipartFile, false);
            }
        }
        File file = new File();
        file.setId(snowflakeIdWorker.nextId());
        String fileName = multipartFile.getOriginalFilename().substring(0, multipartFile.getOriginalFilename().lastIndexOf("."));
        file.setName(fileName);
        file.setSuffix(suffix);
        int typeId = fileUploadDTO.getTypeId();
        file.setTypeId(typeId);
        long size = multipartFile.getSize();
        FileSizeDTO fileSizeDTO = fileSizeUtil.getFileSize(size);
        // 将文件数据存到数据库
        file.setSize(fileSizeDTO.getSize());
        file.setSizeSuffix(fileSizeDTO.getLevel());
        Date date = new Date();
        file.setCreateTime(date);
        file.setUserId(hostHolder.getUserId());
        file.setPath(filePath);
        file.setMd5(md5);
        file.setComeFromId(fileUploadDTO.getComeFromId());
        fileMapper.insertFile(file);
        return filePath;
    }

    /**
     * 文件删除
     *
     * @param fileDeleteDTO
     */
    @Override
    public void deleteFile(FileDeleteDTO fileDeleteDTO) {
        long id = fileDeleteDTO.getId();
        fileMapper.updateStatusById(id);
    }


    /**
     * 文件下载
     */
    @Override
    public FileDownloadVO fileDownload(FileDownloadDTO fileDownloadDTO) {
        Long fileId = fileDownloadDTO.getFileId();
        String fileUrl = fileMapper.selectPathById(fileId);
        byte[] bytes = fileDfsUtil.download(fileUrl);
        String suffix = fileUrl.substring(fileUrl.indexOf(".") + 1);
        List<Long> ids = fileMapper.getIdByFileUrl(fileUrl);
        Long id = ids.get(0);
        String prefixName = fileMapper.selectFileNameById(id);
        String wholeName = prefixName + suffix;
        FileDownloadVO fileDownloadVO = new FileDownloadVO();
        fileDownloadVO.setWholeName(wholeName);
        fileDownloadVO.setBytes(bytes);
        int userId = hostHolder.getUserId();
        // 保存文件和用户的下载关系
        fileService.addUserFileDownloadInfo(fileId, userId);
        return fileDownloadVO;
    }

    /**
     * 获取文件的推荐文件信息
     *
     */
    @Override
    public FileRecommendVO getRecommendInfo(FileRecommendDTO fileRecommendDTO) {
        FileRecommendVO fileRecommendVO = new FileRecommendVO();
        long fileId = fileRecommendDTO.getFileId();
        fileRecommendVO.setFileId(fileId);
        List<FileRecommendResVO> infos = fileMapper.getFileCommendInfoByFileId(fileId);
        fileRecommendVO.setFileRecommendResVOList(infos);
        return fileRecommendVO;
    }

    /**
     * 新增文件的推荐文件
     *
     * @param fileAddRecommendDTO
     */
    @Override
    public void addRecommendFileInfo(FileAddRecommendDTO fileAddRecommendDTO) {
        // 此排序信息先暂时不走缓存哈
        int sortOrder = fileAddRecommendDTO.getSortOrder();
        Long fileId = fileAddRecommendDTO.getFileId();
        if(sortOrder > FILE_RECOMMEND_ENUM.MAX_COUNT.getType()) {
            throw new DescribeException(ErrorCode.FILE_RECOMMEND_RECOMMEND_ORDER_ERROR.getCode(), ErrorCode.FILE_RECOMMEND_RECOMMEND_ORDER_ERROR.getMsg());
        } else if(sortOrder == 1) {
            // 确保插入推荐顺序为1之前没有过数据
            Integer id1 = fileMapper.selectIdByFileIdAndSortOrder(fileId, sortOrder);
            if(id1 != null) {
                throw new DescribeException(ErrorCode.FILE_RECOMMEND_EXISTS_FIRST_ERROR.getCode(), ErrorCode.FILE_RECOMMEND_EXISTS_FIRST_ERROR.getMsg());
            }
        } else {
            // 先保证前面的推荐都是有值的
            List<Integer> showOrders = fileMapper.selectShowOrdersByFileId(fileId);
            showOrders.add(sortOrder);
            Collections.sort(showOrders);
            int compareShowOrder = 0;
            for(int showOrder : showOrders) {
                compareShowOrder ++;
                if(compareShowOrder != showOrder) {
                    throw new DescribeException(ErrorCode.FILE_RECOMMEND_INTERVAL_RECOMMEND_ORDER_ERROR.getCode(), ErrorCode.FILE_RECOMMEND_INTERVAL_RECOMMEND_ORDER_ERROR.getMsg());
                }
            }
        }
        // 插入的推荐资料信息没有重复的
        List<Long> recommendFileIds = fileMapper.selectRecommendFileIdByFileId(fileId);
        long recommendFileId = fileAddRecommendDTO.getRecommendFileId();
        for(long recommendFileIdCompare : recommendFileIds) {
            if(recommendFileIdCompare == recommendFileId) {
                throw new DescribeException(ErrorCode.FILE_RECOMMEND_EXISTS_THIS_RECOMMEND_ERROR.getCode(), ErrorCode.FILE_RECOMMEND_EXISTS_THIS_RECOMMEND_ERROR.getMsg());
            }
        }
        fileMapper.insertFileRecommendInfo(fileAddRecommendDTO);
    }

    /**
     * 删除文件的推荐文件
     */
    @Override
    public void deleteRecommendFileInfo(FileDeleteRecommendDTO fileDeleteRecommendDTO) {
        long fileId = fileDeleteRecommendDTO.getFileId();
        int showOrderCount = fileMapper.selectCountByFileId(fileId);
        fileMapper.deleteRecommendInfo(fileDeleteRecommendDTO);
        int showOrder = fileDeleteRecommendDTO.getSortOrder();
        if(showOrder < showOrderCount) {
            for(int i = showOrder + 1; i <= showOrderCount; i++) {
                FileUpdateRecommendDO fileUpdateRecommendDO = new FileUpdateRecommendDO();
                fileUpdateRecommendDO.setFileId(fileId);
                fileUpdateRecommendDO.setSortOrder(i);
                fileMapper.updateRecommendInfo(fileUpdateRecommendDO);
            }
        }
    }

    /**
     * 上传图片
     * @param multipartFile
     * @param fileUploadDTO
     */
    @Override
    public String uploadPicture(MultipartFile multipartFile, FileUploadDTO fileUploadDTO) throws Exception {
        String filePath;
        String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
        int support = fileMapper.selectSupportBySuffix(suffix);
        if(support != FILE_TYPE_ENUM.PICTURE.getType()) {
            throw new DescribeException(ErrorCode.FILE_MARKDOWN_NO_PICTURE_ERROR.getCode(), ErrorCode.FILE_MARKDOWN_NO_PICTURE_ERROR.getMsg());
        } else {
            filePath = fileUpload(multipartFile, fileUploadDTO);
        }
        return filePath;
    }

    /**
     * 向上移
     *
     * @param fileUpAndDownRecommendDTO
     */
    @Override
    public void upRecommendFileInfo(FileUpAndDownRecommendDTO fileUpAndDownRecommendDTO) {
        long fileId = fileUpAndDownRecommendDTO.getFileId();
        // TODO 此处后期改成并发操作
        int sortOrder = fileUpAndDownRecommendDTO.getSortOrder();
        int sortOrder1 = sortOrder - 1;
        FileUpdateRecommendDO fileUpdateRecommendDO = new FileUpdateRecommendDO();
        fileUpdateRecommendDO.setFileId(fileId);
        fileUpdateRecommendDO.setSortOrder(sortOrder);
        fileMapper.upRecommendSortOrder(fileUpdateRecommendDO);
        FileUpdateRecommendDO fileUpdateRecommendDO1 = new FileUpdateRecommendDO();
        fileUpdateRecommendDO1.setFileId(fileId);
        fileUpdateRecommendDO1.setSortOrder(sortOrder1);
        fileMapper.downRecommendSortOrder(fileUpdateRecommendDO1);
    }

    /**
     * 向下移
     *
     * @param fileUpAndDownRecommendDTO
     */
    @Override
    public void downRecommendFileInfo(FileUpAndDownRecommendDTO fileUpAndDownRecommendDTO) {
        long fileId = fileUpAndDownRecommendDTO.getFileId();
        // TODO 此处后期改成并发操作
        int sortOrder = fileUpAndDownRecommendDTO.getSortOrder();
        int sortOrder1 = sortOrder + 1;
        FileUpdateRecommendDO fileUpdateRecommendDO = new FileUpdateRecommendDO();
        fileUpdateRecommendDO.setFileId(fileId);
        fileUpdateRecommendDO.setSortOrder(sortOrder);
        fileMapper.downRecommendSortOrder(fileUpdateRecommendDO);
        FileUpdateRecommendDO fileUpdateRecommendDO1 = new FileUpdateRecommendDO();
        fileUpdateRecommendDO1.setFileId(fileId);
        fileUpdateRecommendDO1.setSortOrder(sortOrder1);
        fileMapper.upRecommendSortOrder(fileUpdateRecommendDO1);
    }


}
