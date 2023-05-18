package com.cqtalk.dao;

import com.cqtalk.entity.count.vo.CollectPostVO;
import com.cqtalk.entity.post.*;
import com.cqtalk.entity.post.dto.UpdatePostDTO;
import com.cqtalk.entity.post.vo.PostCountListPageVO;
import com.cqtalk.entity.post.vo.TitleContentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    void addPost(Post post);

    void updateStateById(Long id);

    Post selectPostById(Long id);

    void addCountInfo(Long id);

    List<Post> getPostList(Integer page);

    List<Post> getPostListByType(Integer page, Integer type);

    List<PostCountListPageVO> getListPageCountByListId(List<Long> ids);

    Integer selectPostCount();

    void updatePostById(UpdatePostDTO updatePostDTO);

    void updateStateBanById(Long id);

    Integer selectStateById(Long id);

    Integer selectUserIdById(Long id);

    CollectPostVO selectCollectInfoById(Long id);

    TitleContentVO selectContentAndTitleById(Long id);

    Integer selectPostCountByType(Integer type);

    void insertColumn(AddColumnDO addColumnDO);

}
