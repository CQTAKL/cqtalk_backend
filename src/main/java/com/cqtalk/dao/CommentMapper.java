package com.cqtalk.dao;

import com.cqtalk.entity.comment.Comment;
import com.cqtalk.entity.comment.dto.CommentDataDTO;
import com.cqtalk.entity.comment.dto.GetCommentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {

    List<CommentDataDTO> getAllInfo(GetCommentDTO getCommentDTO);

    void insertCommentInfo(Comment comment);

    void updateStateById(Long id);

    Integer selectUserIdById(Long id);

}
