package com.cqtalk.service.toc.entity.impl;

import com.cqtalk.dao.CountMapper;
import com.cqtalk.dao.FileMapper;
import com.cqtalk.dao.PostMapper;
import com.cqtalk.dao.UserMapper;
import com.cqtalk.entity.count.*;
import com.cqtalk.entity.count.dto.*;
import com.cqtalk.entity.count.vo.*;
import com.cqtalk.enumerate.entityType.ENTITY_TYPE_ENUM;
import com.cqtalk.service.toc.entity.CountService;
import com.cqtalk.util.redis.RedisKeyUtil;
import com.cqtalk.util.user.info.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CountServiceImpl implements CountService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private CountMapper countMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileMapper fileMapper;

    // 进行点赞操作
    public void like(EntityLikeDTO entityLikeDTO) {
        int entityType = entityLikeDTO.getEntityType();
        long entityId = entityLikeDTO.getEntityId();
        int userId = entityLikeDTO.getUserId();
        // 确保并发操作
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
                boolean isMember = operations.opsForSet().isMember(entityLikeKey, userId);
                operations.multi();
                if (isMember) { // 已经点赞了
                    operations.opsForSet().remove(entityLikeKey, userId);
                } else { // 尚未点赞
                    operations.opsForSet().add(entityLikeKey, userId);
                }
                return operations.exec();
            }
        });
    }

    // 查询某实体点赞的数量
    public long findEntityLikeCount(EntityLikeCountDTO entityLikeCountDTO) {
        int entityType = entityLikeCountDTO.getEntityType();
        long entityId = entityLikeCountDTO.getEntityId();
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    // 查询某人对某实体的点赞状态
    public int findEntityLikeStatus(EntityLikeDTO entityLikeDTO) {
        int userId = entityLikeDTO.getUserId();
        int entityType = entityLikeDTO.getEntityType();
        long entityId = entityLikeDTO.getEntityId();
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
    }

    public long findEntityCollectCount(EntityCollectCountDTO entityCollectCountDTO) {
        int entityType = entityCollectCountDTO.getEntityType();
        long entityId = entityCollectCountDTO.getEntityId();
        String entityCollectKey = RedisKeyUtil.getEntityCollectKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityCollectKey);
    }

    /**
     * 收藏一个内容
     * @param entityCollectDTO
     */
    @Override
    public void collect(EntityCollectDTO entityCollectDTO) {
        int type = entityCollectDTO.getType();
        long entityId = entityCollectDTO.getEntityId();
        Integer userId = hostHolder.getUserId();
        // 确保并发操作
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String entityCollectKey = RedisKeyUtil.getEntityCollectKey(type, entityId);
                operations.multi();
                // 1 在缓存中先添加这个实体类和用户信息的对应关系（采用set实现）
                operations.opsForSet().add(entityCollectKey, userId);
                return operations.exec();
            }
        });
        // 2 往数据库中存放响应数据
        EntityCollectDO entityCollectDO = new EntityCollectDO();
        entityCollectDO.setType(entityCollectDTO.getType());
        entityCollectDO.setEntityId(entityCollectDTO.getEntityId());
        entityCollectDO.setLabelName(entityCollectDTO.getLabelName());
        entityCollectDO.setUserId(userId);
        entityCollectDO.setCreateTime(new Date());
        countMapper.insertCollectInfo(entityCollectDO);
    }

    /**
     * 返回用户的收藏的所有标签的内容
     * @return
     */
    @Override
    public EntityCollectLabelVO userCollectLabel() {
        Integer userId = hostHolder.getUserId();
        List<String> labels = countMapper.selectLabelNameByUserId(userId);
        EntityCollectLabelVO entityCollectLabelVO = new EntityCollectLabelVO();
        entityCollectLabelVO.setUserId(userId);
        entityCollectLabelVO.setLabels(labels);
        return entityCollectLabelVO;
    }

    /**
     * 返回某人对某实体类的收藏状态
     */
    @Override
    public Integer findCollectState(EntityCollectStateDTO entityCollectStateDTO) {
        int entityType = entityCollectStateDTO.getEntityType();
        long entityId = entityCollectStateDTO.getEntityId();
        int userId = entityCollectStateDTO.getUserId();
        String entityCollectKey = RedisKeyUtil.getEntityCollectKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityCollectKey, userId) ? 1 : 0;
    }

    @Override
    public void uncollected(EntityUnCollectDTO entityUnCollectDTO) {
        int entityType = entityUnCollectDTO.getEntityType();
        long entityId = entityUnCollectDTO.getEntityId();
        int userId = entityUnCollectDTO.getUserId();
        String entityCollectKey = RedisKeyUtil.getEntityCollectKey(entityType, entityId);
        redisTemplate.opsForSet().remove(entityCollectKey, userId);
    }

    @Override
    public List<CollectInfoVO> userCollect(int userId, Integer entityType) {
        List<CollectInfoVO> collectInfoVOs = new ArrayList<>();
        List<CollectDO> collectInfos = countMapper.selectInfoByUserId(userId);
        for(CollectDO collectDO : collectInfos) {
            CollectInfoVO collectInfoVO = new CollectInfoVO();
            long id = collectDO.getId();
            int type = collectDO.getType();
            String label = collectDO.getLabelName();
            Date createTime = collectDO.getCreateTime();
            collectInfoVO.setEntityType(type);
            collectInfoVO.setEntityId(id);
            collectInfoVO.setLabel(label);
            collectInfoVO.setCreateTime(createTime);
            int authUserId = 0;
            // TODO: 以后可以在这里添加收藏的类型，目前仅支持帖子和文件
            if((entityType == 0 || entityType == ENTITY_TYPE_ENUM.POST.getType()) && type == ENTITY_TYPE_ENUM.POST.getType()) {
                CollectPostVO collectPostVO = postMapper.selectCollectInfoById(id);
                String title = collectPostVO.getTitle();
                String content = collectPostVO.getContent();
                collectInfoVO.setTitle(title);
                collectInfoVO.setContent(content);
                authUserId = collectPostVO.getUserId();
            } else if((entityType == 0 || entityType == ENTITY_TYPE_ENUM.FILE.getType()) && type == ENTITY_TYPE_ENUM.FILE.getType()) {
                CollectFileVO collectFileVO = fileMapper.selectFileInfoById(id);
                String title = collectFileVO.getName();
                String content = collectFileVO.getContent();
                collectInfoVO.setTitle(title);
                collectInfoVO.setContent(content);
                authUserId = collectFileVO.getUserId();
            }
            CollectNameVO collectNameVO = userMapper.selectNameInfoByUserId(authUserId);
            collectInfoVO.setAuthUserId(authUserId);
            collectInfoVO.setNickName(collectNameVO.getNickName());
            collectInfoVO.setRealName(collectNameVO.getRealName());
            collectInfoVOs.add(collectInfoVO);
        }
        return collectInfoVOs;
    }

    @Override
    public void userFollowColumn(FollowColumnDTO followColumnDTO) {
        countMapper.insertFollowColumnInfo(followColumnDTO);
    }

    @Override
    public void userUnfollowColumn(FollowColumnDTO followColumnDTO) {
        countMapper.deleteFollowColumnInfo(followColumnDTO);
    }

    @Override
    public Boolean userFollowColumnState(FollowColumnDTO followColumnDTO) {
        boolean flag = false;
        Integer res = countMapper.selectFollowColumnInfoById(followColumnDTO);
        if(res == 1) {
            flag = true;
        }
        return flag;
    }

    /**
     * 增加实体类的浏览量
     * @param entityAddBrowseDTO
     */
    @Override
    public void addBrowseCount(EntityAddBrowseDTO entityAddBrowseDTO) {
        int entityType = entityAddBrowseDTO.getEntityType();
        long entityId = entityAddBrowseDTO.getEntityId();
        String entityBrowseKey = RedisKeyUtil.getEntityBrowseKey(entityType, entityId);
        redisTemplate.opsForValue().increment(entityBrowseKey); // 默认加一
    }

    @Override
    public Integer findEntityBrowseCount(EntityBrowseCountDTO entityBrowseCountDTO) {
        int entityType = entityBrowseCountDTO.getEntityType();
        long entityId = entityBrowseCountDTO.getEntityId();
        String entityBrowseKey = RedisKeyUtil.getEntityBrowseKey(entityType, entityId);
        return (Integer) redisTemplate.opsForValue().get(entityBrowseKey);
    }


}
