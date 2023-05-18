package com.cqtalk.service.toc.comment;

import com.cqtalk.entity.comment.AddCommentDTO;
import com.cqtalk.entity.comment.DeleteCommentDTO;
import com.cqtalk.entity.comment.GetCommentDTO;
import com.cqtalk.entity.comment.GetCommentVO;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public interface CommentService {

    GetCommentVO showAllComment(GetCommentDTO getCommentDTO);

    void addComment(AddCommentDTO addCommentDTO) throws ExecutionException, InterruptedException;

    void deleteComment(DeleteCommentDTO deleteCommentDTO);

}
