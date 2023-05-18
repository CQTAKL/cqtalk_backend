package com.cqtalk.util.file;

import com.cqtalk.dao.FileMapper;
import com.cqtalk.enumerate.file.FILE_TYPE_ENUM;
import com.cqtalk.util.returnObject.DescribeException;
import com.cqtalk.util.returnObject.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileTypeUtil {

    @Autowired
    private FileMapper fileMapper;

    /**
     * 查看是否支持此文件的类型
     * @param suffix 传入文件后缀
     * @return true支持 false不支持
     */
    public void checkFileType(String suffix) {
        Integer id = fileMapper.selectIdBySuffix(suffix);
        if(id == null) {
            throw new DescribeException(ErrorCode.FILE_FORMAT_NOT_SUPPORT_ERROR.getCode(), ErrorCode.FILE_FORMAT_NOT_SUPPORT_ERROR.getMsg());
        } else {
            int support = fileMapper.selectSupportById(id);
            if(support == 0) {
                throw new DescribeException(ErrorCode.FILE_FORMAT_NOT_SUPPORT_ERROR.getCode(), ErrorCode.FILE_FORMAT_NOT_SUPPORT_ERROR.getMsg());
            }
        }
    }

    /**
     * 查看文件类型是否是图片格式
     * @param suffix 传入文件后缀
     * @return true支持 false不支持
     */
    public void checkPictureType(String suffix) {
        Integer id = fileMapper.selectIdBySuffix(suffix);
        if(id == null) {
            throw new DescribeException(ErrorCode.FILE_FORMAT_NOT_SUPPORT_ERROR.getCode(), ErrorCode.FILE_FORMAT_NOT_SUPPORT_ERROR.getMsg());
        } else {
            int support = fileMapper.selectSupportById(id);
            if(support != FILE_TYPE_ENUM.PICTURE.getType()) {
                throw new DescribeException(ErrorCode.FILE_FORMAT_NOT_SUPPORT_ERROR.getCode(), ErrorCode.FILE_FORMAT_NOT_SUPPORT_ERROR.getMsg());
            }
        }
    }

}
