package com.cqtalk.dao;

import com.cqtalk.entity.comment.Comment;
import com.cqtalk.entity.comment.CommentDataDTO;
import com.cqtalk.entity.comment.GetCommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<CommentDataDTO> getAllInfo(GetCommentDTO getCommentDTO);

    void insertCommentInfo(Comment comment);

    void updateStateById(Long id);

    Integer selectUserIdById(Long id);

}
