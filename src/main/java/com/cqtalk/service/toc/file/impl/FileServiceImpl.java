package com.cqtalk.service.toc.file.impl;

import com.cqtalk.service.toc.file.FileService;
import com.cqtalk.util.redis.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void addUserFileDownloadInfo(Long fileId, Integer userId) {
        String fileUserKey = RedisKeyUtil.getUserFileDownloadKey(fileId, userId);
        redisTemplate.opsForValue().set(fileUserKey, true);
    }

}
