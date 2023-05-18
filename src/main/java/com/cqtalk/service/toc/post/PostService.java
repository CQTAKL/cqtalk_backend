package com.cqtalk.service.toc.post;

import com.cqtalk.entity.post.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public interface PostService {

    void addPost(AddPostDTO addPostDTO, int format) throws ExecutionException, InterruptedException;

    void addRichTextPost(AddPostDTO addPostDTO) throws ExecutionException, InterruptedException;

    void addMdPost(AddPostDTO addPostDTO) throws ExecutionException, InterruptedException;

    void deletePost(DeletePostDTO deletePostDTO);

    PostVO getPostInfo(Long id);

    PostListVO getPostListInfo(Integer type, Integer page);

    void updatePost(UpdatePostDTO updatePostDTO);

    void banPost(BanPostDTO banPostDTO);

    void addColumn(AddColumnDTO addColumnDTO);


}
