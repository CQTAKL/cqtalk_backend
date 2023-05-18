package com.cqtalk.service.toc.entity;

import com.cqtalk.entity.count.dto.*;
import com.cqtalk.entity.count.vo.CollectInfoVO;
import com.cqtalk.entity.count.vo.EntityCollectLabelVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CountService {

    /**
     * 进行点赞操作
     * @param entityLikeDTO
     */
    void like(EntityLikeDTO entityLikeDTO);

    /**
     * 查询某实体被点赞的数量
     * @param entityLikeCountDTO
     * @return
     */
    long findEntityLikeCount(EntityLikeCountDTO entityLikeCountDTO);

    /**
     * 查询某人对某实体的点赞状态
     * @return
     */
    int findEntityLikeStatus(EntityLikeDTO entityLikeDTO);

    /**
     * 查询某实体被收藏的数量
     * @param entityCollectCountDTO
     * @return
     */
    long findEntityCollectCount(EntityCollectCountDTO entityCollectCountDTO);

    /**
     * 增加实体类的浏览量
     */
    void addBrowseCount(EntityAddBrowseDTO entityAddBrowseDTO);

    void collect(EntityCollectDTO entityCollectDTO);

    EntityCollectLabelVO userCollectLabel();

    Integer findEntityBrowseCount(EntityBrowseCountDTO entityBrowseCountDTO);

    Integer findCollectState(EntityCollectStateDTO entityCollectStateDTO);

    void uncollected(EntityUnCollectDTO entityUnCollectDTO);

    List<CollectInfoVO> userCollect(int userId, Integer entityType);

    void userFollowColumn(FollowColumnDTO followColumnDTO);

    void userUnfollowColumn(FollowColumnDTO followColumnDTO);

    Boolean userFollowColumnState(FollowColumnDTO followColumnDTO);

}
