package com.cqtalk.service.toc.comment;

import com.cqtalk.entity.comment.dto.AddCommentDTO;
import com.cqtalk.entity.comment.dto.DeleteCommentDTO;
import com.cqtalk.entity.comment.dto.GetCommentDTO;
import com.cqtalk.entity.comment.vo.GetCommentVO;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public interface CommentService {

    GetCommentVO showAllComment(GetCommentDTO getCommentDTO);

    void addComment(AddCommentDTO addCommentDTO) throws ExecutionException, InterruptedException;

    void deleteComment(DeleteCommentDTO deleteCommentDTO);

}
