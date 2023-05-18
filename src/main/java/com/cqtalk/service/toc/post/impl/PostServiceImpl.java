package com.cqtalk.service.toc.post.impl;

import com.cqtalk.dao.PostMapper;
import com.cqtalk.dao.UserMapper;
import com.cqtalk.entity.post.*;
import com.cqtalk.entity.search.PostSearchInfo;
import com.cqtalk.enumerate.entityType.ENTITY_TYPE_ENUM;
import com.cqtalk.enumerate.post.POST_FORMAT_ENUM;
import com.cqtalk.enumerate.post.POST_STATE_ENUM;
import com.cqtalk.service.toc.post.PostService;
import com.cqtalk.util.es.ElasticSearchUtil;
import com.cqtalk.util.getid.SnowflakeIdWorker;
import com.cqtalk.util.ip.ProvinceInfo;
import com.cqtalk.util.returnObject.DescribeException;
import com.cqtalk.util.returnObject.ErrorCode;
import com.cqtalk.util.sensitive.SensitiveWord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private ProvinceInfo provinceInfo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SensitiveWord sensitiveWord;

    @Autowired
    private ElasticSearchUtil esUtil;

    @Override
    public void addPost(AddPostDTO addPostDTO, int format) throws ExecutionException, InterruptedException {
        // 具体做法：
        // 1 往数据库中插入
        // 2 往es中插入
        // 3 （redis）初始化浏览量、点赞量、点踩量等均为1
        Post post = new Post();
        // 雪花算法生成id
        Long id = snowflakeIdWorker.nextId();
        post.setId(id);
        post.setUserId(addPostDTO.getUserId());
        String title = sensitiveWord.filterInfo(addPostDTO.getTitle());
        post.setTitle(title);
        String content = sensitiveWord.filterInfo(addPostDTO.getContent());
        post.setContent(content);
        String briefIntro = sensitiveWord.filterInfo(addPostDTO.getBriefIntro());
        post.setBriefIntro(briefIntro);
        Date date = new Date();
        post.setCreateTime(date);
        post.setLocationId(provinceInfo.getProvinceId());
        post.setColumnId(addPostDTO.getColumnId());
        post.setFormat(format);
//        post.setLocationId();
        post.setState(POST_STATE_ENUM.NORMAL.getType());
        postMapper.addPost(post);
        postMapper.addCountInfo(id);
        PostSearchInfo postSearchInfo = new PostSearchInfo();
        postSearchInfo.setEntityType(ENTITY_TYPE_ENUM.POST.getType());
        postSearchInfo.setEntityId(id);
        postSearchInfo.setBriefIntro(briefIntro);
        postSearchInfo.setTitle(title);
        postSearchInfo.setContent(content);
        postSearchInfo.setType(addPostDTO.getType());
        esUtil.insertESPost(postSearchInfo); // 向es中插入帖子
        // TODO
    }

    @Override
    public void addRichTextPost(AddPostDTO addPostDTO) throws ExecutionException, InterruptedException {
        addPost(addPostDTO, POST_FORMAT_ENUM.RICHTEXT.getType());
    }

    /**
     * 添加一个帖子
     */
    @Override
    public void addMdPost(AddPostDTO addPostDTO) throws ExecutionException, InterruptedException {
        addPost(addPostDTO, POST_FORMAT_ENUM.MARKDOWN.getType());
    }

    @Override
    public void deletePost(DeletePostDTO deletePostDTO) {
        long id = deletePostDTO.getId();
        postMapper.updateStateById(id);
    }

    @Override
    public PostVO getPostInfo(Long id) {
        Integer state = postMapper.selectStateById(id);
        if(state == POST_STATE_ENUM.BANNED.getType()) {
            throw new DescribeException(ErrorCode.POST_BANNED_ERROR.getCode(), ErrorCode.POST_BANNED_ERROR.getMsg());
        }
        if(state == POST_STATE_ENUM.DELETED.getType()) {
            throw new DescribeException(ErrorCode.POST_DELETED_ERROR.getCode(), ErrorCode.POST_DELETED_ERROR.getMsg());
        }
        PostVO postVO = new PostVO();
        Post post = postMapper.selectPostById(id);
        PostUserVO postUserVO = userMapper.getPostShowInfoByUserId(post.getUserId());
        postVO.setId(post.getId());
        postVO.setPostUserVO(postUserVO);
        postVO.setTitle(post.getTitle());
        postVO.setContent(post.getContent());
        postVO.setCreateTime(post.getCreateTime());
        postVO.setFormat(post.getFormat());
        postVO.setLocationId(post.getLocationId());
        return postVO;
    }

    @Override
    public PostListVO getPostListInfo(Integer type, Integer page) {
        Integer count;
        if(type == 0) {
            count = postMapper.selectPostCount();
        } else {
            count = postMapper.selectPostCountByType(type);
        }
        count /= 10;
        count += 1;
        if(page < 1) {
            page = 1;
        }
        if(page > count) {
            page = count;
        }
        page -= 1;
        page *= 10;
        List<Post> postList;
        if(type == 0) {
            postList = postMapper.getPostList(page);
        } else {
            postList = postMapper.getPostListByType(page, type);
        }
        PostListVO postListVO = new PostListVO();
        List<Long> ids = new ArrayList<>();
        for(Post postInfo: postList) {
            PostListSingleVO postListSingleVO = new PostListSingleVO();
            int userId = postInfo.getUserId();
            PostUserVO postUserVO = userMapper.getPostShowInfoByUserId(userId);
            postListSingleVO.setPostUserVO(postUserVO);
            postListSingleVO.setId(postInfo.getId());
            postListSingleVO.setTitle(postInfo.getTitle());
            postListSingleVO.setBriefIntro(postInfo.getBriefIntro());
            postListSingleVO.setFormat(postInfo.getFormat());
            postListSingleVO.setColumnId(postInfo.getColumnId());
            postListSingleVO.setCreateTime(postInfo.getCreateTime());
            postListSingleVO.setLocationId(postInfo.getLocationId());
            ids.add(postInfo.getId());
            postListVO.addPostListSingleInfo(postListSingleVO);
        }
        List<PostCountListPageVO> countsInfo = postMapper.getListPageCountByListId(ids);
        for(PostCountListPageVO countInfo : countsInfo) {
            Long postId = countInfo.getPostId();
            List<PostListSingleVO> postListSingleVOList = postListVO.getPostListSingleVOList();
            for(PostListSingleVO postListSingleVO : postListSingleVOList) {
                Long idCompare = postListSingleVO.getId();
                if(Objects.equals(idCompare, postId)) {
                    postListSingleVO.setLikeCount(countInfo.getLike());
                    postListSingleVO.setBrowseCount(countInfo.getBrowse());
                    postListSingleVO.setCommentCount(countInfo.getComment());
                }
            }
        }
        return postListVO;
    }

    @Override
    public void updatePost(UpdatePostDTO updatePostDTO) {
        Integer state = postMapper.selectStateById(updatePostDTO.getId());
        if(state == POST_STATE_ENUM.BANNED.getType()) {
            throw new DescribeException(ErrorCode.POST_BANNED_ERROR.getCode(), ErrorCode.POST_BANNED_ERROR.getMsg());
        }
        if(state == POST_STATE_ENUM.DELETED.getType()) {
            throw new DescribeException(ErrorCode.POST_DELETED_ERROR.getCode(), ErrorCode.POST_DELETED_ERROR.getMsg());
        }
        String title = sensitiveWord.filterInfo(updatePostDTO.getTitle());
        String content = sensitiveWord.filterInfo(updatePostDTO.getContent());
        updatePostDTO.setTitle(title);
        updatePostDTO.setContent(content);
        postMapper.updatePostById(updatePostDTO);
    }

    @Override
    public void banPost(BanPostDTO banPostDTO) {
        postMapper.updateStateBanById(banPostDTO.getId());
    }

    @Override
    public void addColumn(AddColumnDTO addColumnDTO) {
        AddColumnDO addColumnDO = new AddColumnDO();
        addColumnDO.setContent(addColumnDTO.getContent());
        addColumnDO.setBriefIntro(addColumnDTO.getBriefIntro());
        addColumnDO.setTitle(addColumnDTO.getTitle());
        addColumnDO.setCreateTime(new Date());
        postMapper.insertColumn(addColumnDO);
    }

}
