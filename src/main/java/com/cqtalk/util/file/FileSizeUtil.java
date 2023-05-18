package com.cqtalk.util.file;

import com.cqtalk.entity.file.dto.FileSizeDTO;
import com.cqtalk.enumerate.file.FILE_MAX_LIMIT_ENUM;
import com.cqtalk.enumerate.file.FILE_SIZE_ENUM;
import com.cqtalk.util.returnObject.DescribeException;
import com.cqtalk.util.returnObject.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class FileSizeUtil {
    
    public FileSizeDTO getFileSize(Long size) {
        String level = FILE_SIZE_ENUM.LEVEL_OF_B.getLevel();
        FileSizeDTO fileSizeDTO = new FileSizeDTO();
        if(size / 1024 == 0) {
            fileSizeDTO.setSize(size.intValue());
            fileSizeDTO.setLevel(level);
        } else if(size / 1024 != 0 & size / (1024 * 1024) == 0) {
            size = size / 1024;
            fileSizeDTO.setSize(size.intValue());
            level = FILE_SIZE_ENUM.LEVEL_OF_KB.getLevel();
            fileSizeDTO.setLevel(level);
        } else if(size / (1024 * 1024) != 0 & size / (1024 * 1024 * 1024) == 0) {
            size = size / (1024 * 1024);
            fileSizeDTO.setSize(size.intValue());
            level = FILE_SIZE_ENUM.LEVEL_OF_MB.getLevel();
            fileSizeDTO.setLevel(level);
        } else if(size / (1024 * 1024 * 1024) != 0) {
            size = size / (1024 * 1024 * 1024);
            fileSizeDTO.setSize(size.intValue());
            level = FILE_SIZE_ENUM.LEVEL_OF_GB.getLevel();
            fileSizeDTO.setLevel(level);
        } else {
            throw new DescribeException(ErrorCode.FILE_TOO_BIG_ERROR.getCode(), ErrorCode.FILE_TOO_BIG_ERROR.getMsg());
        }
        return fileSizeDTO;
    }

    public void checkSize(Long size) {
        if (size > FILE_MAX_LIMIT_ENUM.MAX_SIZE.getType()) {
            throw new DescribeException(ErrorCode.FILE_SIZE_TOO_BIG_LIMIT.getCode(), ErrorCode.FILE_SIZE_TOO_BIG_LIMIT.getMsg());
        }
    }
    
}
