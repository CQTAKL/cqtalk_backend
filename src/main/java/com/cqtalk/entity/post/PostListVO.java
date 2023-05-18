package com.cqtalk.entity.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostListVO {

    private List<PostListSingleVO> postListSingleVOList = new ArrayList<>();

    public void addPostListSingleInfo(PostListSingleVO postListSingleVO) {
        postListSingleVOList.add(postListSingleVO);
    }

}
