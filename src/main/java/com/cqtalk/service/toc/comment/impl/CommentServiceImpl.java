package com.cqtalk.service.toc.comment.impl;

import com.cqtalk.dao.CommentMapper;
import com.cqtalk.dao.UserMapper;
import com.cqtalk.entity.comment.*;
import com.cqtalk.entity.comment.AddCommentDTO;
import com.cqtalk.enumerate.comment.COMMENT_STATE_ENUM;
import com.cqtalk.service.toc.comment.CommentService;
import com.cqtalk.util.getid.SnowflakeIdWorker;
import com.cqtalk.util.ip.ProvinceInfo;
import com.cqtalk.util.jwt.JWTUtil;
import com.cqtalk.util.sensitive.SensitiveWord;
import com.cqtalk.util.user.info.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private ProvinceInfo provinceInfo;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SensitiveWord sensitiveWord;

    @Override
    public GetCommentVO showAllComment(GetCommentDTO getCommentDTO) {
        List<CommentDataDTO> comments = commentMapper.getAllInfo(getCommentDTO);
        for(CommentDataDTO commentDataDTO : comments) {
            Integer userId = commentDataDTO.getUserId();
            CommentUserVO commentShow = userMapper.getCommentShowInfoByUserId(userId);
            commentDataDTO.setCommentUserVO(commentShow);
        }
        GetCommentVO getCommentVO = new GetCommentVO();
        getCommentVO.setType(getCommentDTO.getType());
        getCommentVO.setEntityId(getCommentDTO.getEntityId());
        getCommentVO.setCommentInfos(comments);
        return getCommentVO;
    }

    @Override
    public void addComment(AddCommentDTO addCommentDTO) throws ExecutionException, InterruptedException {
        Comment addCommentDO = new Comment();
        // 雪花算法生成id
        Long id = snowflakeIdWorker.nextId();
        addCommentDO.setId(id);
        int userId = hostHolder.getUserId();
        addCommentDO.setUserId(userId);
        String content = sensitiveWord.filterInfo(addCommentDTO.getContent());
        addCommentDO.setContent(content);
        addCommentDO.setParentId(addCommentDTO.getParentId());
        addCommentDO.setAtId(addCommentDTO.getAtId());
        addCommentDO.setType(addCommentDTO.getType());
        addCommentDO.setLocationId(provinceInfo.getProvinceId());
        addCommentDO.setEntityId(addCommentDTO.getEntityId());
        addCommentDO.setState(COMMENT_STATE_ENUM.NORMAL.getType());
        commentMapper.insertCommentInfo(addCommentDO);
    }

    @Override
    public void deleteComment(DeleteCommentDTO deleteCommentDTO) {
        commentMapper.updateStateById(deleteCommentDTO.getId());
    }

}
