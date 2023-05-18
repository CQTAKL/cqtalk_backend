package com.cqtalk.util.file;

import com.cqtalk.dao.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

// 判断文件是否重复
// 检测原理：不同文件的md5值不同，相同文件的MD5值相同
@Component
public class FileDuplicateUtil {

    @Autowired
    private FileMapper fileMapper;

    /**
     * 判断文件是否重复
     * @param md5 文件的md5值
     * @return true 有相同的文件; false 没有相同的文件
     */
    public Boolean duplicateCheck(String md5) {
        List<String> md5s = fileMapper.selectMd5();
        for (String md5Compare : md5s) {
            if(md5Compare.equals(md5)) {
                return true;
            }
        }
        return false;
    }

}
