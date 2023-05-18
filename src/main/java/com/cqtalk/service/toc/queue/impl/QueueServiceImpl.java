package com.cqtalk.service.toc.queue.impl;

import com.cqtalk.dao.FileMapper;
import com.cqtalk.dao.PostMapper;
import com.cqtalk.entity.file.FileTitleContentVO;
import com.cqtalk.entity.post.TitleContentVO;
import com.cqtalk.entity.queue.AddSystemNoticeDTO;
import com.cqtalk.entity.queue.ColumnNoticeVO;
import com.cqtalk.entity.queue.Event;
import com.cqtalk.entity.queue.SystemInfoVO;
import com.cqtalk.service.toc.queue.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class QueueServiceImpl implements QueueService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private FileMapper fileMapper;

    @Override
    public void addSystemNotice(AddSystemNoticeDTO addSystemNoticeDTO) {
        /*
        String redisKey = RedisKeyUtil.getNoticeSystemReadInfoKey(type, id);
        redisTemplate.opsForValue().setBit(redisKey, );*/
        int type = addSystemNoticeDTO.getType();
        long id = addSystemNoticeDTO.getId();
        if(type == 1) {
            TitleContentVO titleContentVO = postMapper.selectContentAndTitleById(id);
        } else if (type == 2) {
            FileTitleContentVO fileTitleContentVO = fileMapper.selectTitleAndContentById(id);
        }
        // 1 把消息放到消息队列里
        Event event = new Event();
//        event.set

    }

    @Override
    public SystemInfoVO getSystemNoticeNoRead(Integer userId) {
        return null;
    }

    @Override
    public ColumnNoticeVO columnNoticeNoRead(Integer userId) {
        // 此处开发采用拉模型
        return null;
    }
}
