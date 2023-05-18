package com.cqtalk.dao;

import com.cqtalk.entity.count.CollectDO;
import com.cqtalk.entity.count.EntityCollectDO;
import com.cqtalk.entity.count.FollowColumnDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CountMapper {

    void insertCollectInfo(EntityCollectDO entityCollectDO);

    List<String> selectLabelNameByUserId(Integer userId);

    List<CollectDO> selectInfoByUserId(Integer userId);

    void insertFollowColumnInfo(FollowColumnDTO followColumnDTO);

    void deleteFollowColumnInfo(FollowColumnDTO followColumnDTO);

    Integer selectFollowColumnInfoById(FollowColumnDTO followColumnDTO);

}
