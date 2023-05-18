package com.cqtalk.dao;

import com.cqtalk.entity.comment.vo.CommentUserVO;
import com.cqtalk.entity.count.vo.CollectNameVO;
import com.cqtalk.entity.post.vo.PostUserVO;
import com.cqtalk.entity.user.*;
import com.cqtalk.entity.user.dto.ModifyEmailDO;
import com.cqtalk.entity.user.vo.UserShowInfoVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    Integer selectIdByEmail(String email);

    void insertUser(User user);

    Integer selectIdByPhone(String phone);

    Integer selectIdByRealName(String realName);

    String selectSaltById(Integer id);

    String selectPasswordById(Integer id);

    String selectHeaderUrlById(Integer id);

    String selectLastHeaderUrlById(Integer id);

    String selectEmailById(Integer id);

    String selectRealNameById(Integer id);

    void updateRealNameAndIdentifyCodeById(Integer id);

    void updateShowInfoById(ShowInfoDO showInfoDO);

    void updatePasswordById(ModifyPasswordDO modifyPasswordDO);

    void updateMajorById(ModifyMajorDO modifyMajorDO);

    void updateUrlById(ModifyUrlDO modifyUrlDO);

    void updateEmailById(ModifyEmailDO modifyEmailDO);

    void updateAuthInfoById(UserIdentifyDO userIdentifyDO);

    int selectTypeById(Integer id);

    PostUserVO getPostShowInfoByUserId(Integer id);

    CommentUserVO getCommentShowInfoByUserId(Integer id);

    CollectNameVO selectNameInfoByUserId(Integer id);

    UserShowInfoVO selectShowInfoById(Integer id);

    void insertUserFollowInfo(UserFollow userFollow);

    Integer selectUserBeFollowedIdByUserId(Integer followUserId);

    void deleteFollowInfo(UserFollow userFollow);

}
