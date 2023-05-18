package com.cqtalk.service.toc.user.impl;

import com.cqtalk.dao.UserMapper;
import com.cqtalk.entity.user.*;
import com.cqtalk.entity.user.dto.*;
import com.cqtalk.entity.user.vo.UserIdentifyVO;
import com.cqtalk.entity.user.vo.UserShowInfoVO;
import com.cqtalk.entity.user.vo.UserShowInfoVO1;
import com.cqtalk.enumerate.email.EMAIL_SEND_TYPE;
import com.cqtalk.service.toc.user.UserInfoService;
import com.cqtalk.util.date.DateUtil;
import com.cqtalk.util.encrypt.MD5Util;
import com.cqtalk.util.file.FileDfsUtil;
import com.cqtalk.util.redis.RedisKeyUtil;
import com.cqtalk.util.returnObject.DescribeException;
import com.cqtalk.util.returnObject.ErrorCode;
import com.cqtalk.util.sensitive.SensitiveWord;
import com.cqtalk.util.user.authentication.IdentifyCodeUtil;
import com.cqtalk.util.user.email.EmailActivationUtil;
import com.cqtalk.util.user.info.HostHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EmailActivationUtil emailActivationUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IdentifyCodeUtil identifyCodeUtil;

    @Autowired
    private FileDfsUtil fileDfsUtil;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SensitiveWord sensitiveWord;

    @Autowired
    private DateUtil dateUtil;

    @Override
    public void modifyShowInfo(ModifyShowInfoDTO modifyShowInfoDTO) {
        int id = hostHolder.getUserId();
        ShowInfoDO showInfoDO = new ShowInfoDO();
        showInfoDO.setId(id);
        if(modifyShowInfoDTO.getBirth() != null) {
            Date date = DateUtil.strToDate1(modifyShowInfoDTO.getBirth());
            showInfoDO.setBirth(date);
        }
        if(modifyShowInfoDTO.getNickName() != null) {
            String nickName = sensitiveWord.filterInfo(modifyShowInfoDTO.getNickName());
            showInfoDO.setNickName(nickName);
        }
        if(modifyShowInfoDTO.getMotto() != null) {
            String motto = sensitiveWord.filterInfo(modifyShowInfoDTO.getMotto());
            showInfoDO.setMotto(motto);
        }
        if(modifyShowInfoDTO.getBriefIntro() != null) {
            String briefIntro = sensitiveWord.filterInfo(modifyShowInfoDTO.getBriefIntro());
            showInfoDO.setBriefIntro(briefIntro);
        }
        userMapper.updateShowInfoById(showInfoDO);
    }

    /**
     * 获取用户的展示信息
     *
     * @param id
     */
    @Override
    public UserShowInfoVO1 getUserShowInfo(Integer id) {
        UserShowInfoVO1 userShowInfoVO1 = new UserShowInfoVO1();
        UserShowInfoVO userShowInfoVO = userMapper.selectShowInfoById(id);
        userShowInfoVO1.setNickName(userShowInfoVO.getNickName());
        userShowInfoVO1.setBirth(DateUtil.dateToString1(userShowInfoVO.getBirth()));
        userShowInfoVO1.setMotto(userShowInfoVO.getMotto());
        userShowInfoVO1.setBriefIntro(userShowInfoVO.getBriefIntro());
        return userShowInfoVO1;
    }

    /**
     * 修改密码
     *
     * @param modifyPasswordDTO
     */
    @Override
    public void modifyPassword(ModifyPasswordDTO modifyPasswordDTO) {
        int id = hostHolder.getUserId();
        ModifyPasswordDO modifyPasswordDO = new ModifyPasswordDO();
        String password = modifyPasswordDTO.getPassword();
        String salt = userMapper.selectSaltById(id);
        password = MD5Util.md5(password + salt);
        modifyPasswordDO.setPassword(password);
        modifyPasswordDO.setId(id);
        userMapper.updatePasswordById(modifyPasswordDO);
    }

    /**
     * 获取用户的邮箱
     */
    @Override
    public void getEmailCode(EmailCodeGetDTO emailCodeGetDTO) {
        String email = emailCodeGetDTO.getEmail();
        emailActivationUtil.getEmailCode(email, EMAIL_SEND_TYPE.MODIFY.getType());
    }

    /**
     * 修改邮箱
     *
     * @param modifyEmailDTO
     */
    @Override
    public void modifyEmail(ModifyEmailDTO modifyEmailDTO) {
        int id = hostHolder.getUserId();
        String redisKey = RedisKeyUtil.getActivationKey(modifyEmailDTO.getEmail());
        if(redisTemplate.opsForValue().get(redisKey) == null) {
            throw new DescribeException(ErrorCode.MODIFY_EMAIL_NOT_GET_CODE_ERROR.getCode(), ErrorCode.MODIFY_EMAIL_NOT_GET_CODE_ERROR.getMsg());
        }
        int code = (int) redisTemplate.opsForValue().get(redisKey);
        int userCode = modifyEmailDTO.getCode();
        if(userCode != code) {
            throw new DescribeException(ErrorCode.MODIFY_EMAIL_CODE_ERROR.getCode(), ErrorCode.MODIFY_EMAIL_CODE_ERROR.getMsg());
        }
        Integer uid = userMapper.selectIdByEmail(modifyEmailDTO.getEmail());
        if(uid != null) {
            throw new DescribeException(ErrorCode.MODIFY_EMAIL_USED_EMAIL_ERROR.getCode(), ErrorCode.MODIFY_EMAIL_USED_EMAIL_ERROR.getMsg());
        }
        ModifyEmailDO modifyEmailDO = new ModifyEmailDO();
        modifyEmailDO.setEmail(modifyEmailDTO.getEmail());
        modifyEmailDO.setId(id);
        userMapper.updateEmailById(modifyEmailDO);
    }

    /**
     * 恢复到上一个路径
     */
    @Override
    public void recoverLastUrl() {
        int id = hostHolder.getUserId();
        String headerUrl = userMapper.selectHeaderUrlById(id);
        String lastHeaderUrl = userMapper.selectLastHeaderUrlById(id);
        if(lastHeaderUrl == null) {
            throw new DescribeException(ErrorCode.MODIFY_NO_LAST_HEADER_URL_ERROR.getCode(), ErrorCode.MODIFY_NO_LAST_HEADER_URL_ERROR.getMsg());
        }
        String tempPath = headerUrl;
        headerUrl = lastHeaderUrl;
        lastHeaderUrl = tempPath;
        ModifyUrlDO modifyUrlDO = new ModifyUrlDO();
        modifyUrlDO.setId(id);
        modifyUrlDO.setHeaderUrl(headerUrl);
        modifyUrlDO.setLastHeaderUrl(lastHeaderUrl);
        userMapper.updateUrlById(modifyUrlDO);
    }

    /**
     * 修改用户头像
     */
    @Override
    public void modifyHeaderUrl(MultipartFile file) throws IOException {
        int id = hostHolder.getUserId();
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String[] compareSuffix = {"jpg", "jpeg", "png"};
        int flag = 0;
        for(String comSuffix : compareSuffix) {
            if (suffix.equals(comSuffix)) {
                flag = 1;
                break;
            }
        }
        if(flag == 0) {
            throw new DescribeException(ErrorCode.MODIFY_NO_FILE_FORMAT_ERROR.getCode(), ErrorCode.MODIFY_NO_FILE_FORMAT_ERROR.getMsg());
        }
        // 将图片上传至服务器
        String newHeaderUrl = fileDfsUtil.upload(file, true);
        String headerUrl = userMapper.selectHeaderUrlById(id);
        ModifyUrlDO modifyUrlDO = new ModifyUrlDO();
        modifyUrlDO.setLastHeaderUrl(headerUrl);
        modifyUrlDO.setId(id);
        modifyUrlDO.setHeaderUrl(newHeaderUrl);
        userMapper.updateUrlById(modifyUrlDO);
    }

    /**
     * 修改专业信息
     *
     * @param modifyMajorDTO
     */
    @Override
    public void modifyMajor(ModifyMajorDTO modifyMajorDTO) {
        int id = hostHolder.getUserId();
        ModifyMajorDO modifyMajorDO = new ModifyMajorDO();
        modifyMajorDO.setMajor(modifyMajorDTO.getMajor());
        modifyMajorDO.setSchool(modifyMajorDTO.getSchool());
        modifyMajorDO.setGraduateMajor(modifyMajorDTO.getGraduateMajor());
        modifyMajorDO.setGraduateSchool(modifyMajorDTO.getGraduateSchool());
        modifyMajorDO.setId(id);
        userMapper.updateMajorById(modifyMajorDO);
    }

    /**
     * 用户实名认证
     *
     * @param userIdentifyDTO
     */
    @Override
    public void userAuth(UserIdentifyDTO userIdentifyDTO) {
        int id = hostHolder.getUserId();
        UserIdentifyVO userIdentifyVO = identifyCodeUtil.identifyUser(userIdentifyDTO.getRealName(), userIdentifyDTO.getIdentifyCode());
        if(!userIdentifyVO.getTrueIdentify()) {
            throw new DescribeException(ErrorCode.USER_IDENTIFY_CODE_ERROR.getCode(), ErrorCode.USER_IDENTIFY_CODE_ERROR.getMsg());
        }
        // 将用户信息写入user表
        User user = new User();
        user.setRealName(userIdentifyDTO.getRealName());
        user.setIdentifyCode(userIdentifyDTO.getIdentifyCode());
        UserIdentifyDO userIdentifyDO = new UserIdentifyDO();
        userIdentifyDO.setIdentifyCode(userIdentifyDTO.getIdentifyCode());
        userIdentifyDO.setRealName(userIdentifyDTO.getRealName());
        userIdentifyDO.setId(id);
        userMapper.updateAuthInfoById(userIdentifyDO);
    }

    @Override
    public String getHeaderUrl(Integer userId) {
        return userMapper.selectHeaderUrlById(userId);
    }

    @Override
    public void followUser(UserFollowDTO userFollowDTO) {
        // 缓存+数据库
        Integer userId = userFollowDTO.getUserId();
        Integer userBeFollowId = userFollowDTO.getUserBeFollowId();
        if(Objects.equals(userId, userBeFollowId)) {
            throw new DescribeException(ErrorCode.USER_FOLLOW_ONE_PERSON_ERROR.getCode(), ErrorCode.USER_FOLLOW_ONE_PERSON_ERROR.getMsg());
        }
        Integer userBeFollowIdCompare = userMapper.selectUserBeFollowedIdByUserId(userId);
        if(Objects.equals(userBeFollowIdCompare, userBeFollowId)) {
            throw new DescribeException(ErrorCode.USER_FOLLOW_ALREADY_ERROR.getCode(), ErrorCode.USER_FOLLOW_ALREADY_ERROR.getMsg());
        }
        String userFollowKey = RedisKeyUtil.getUserFollowKey(userId);
        redisTemplate.opsForSet().add(userFollowKey, userBeFollowId);
        String userBeFollowedKey = RedisKeyUtil.getUserBeFollowedKey(userBeFollowId);
        redisTemplate.opsForSet().add(userBeFollowedKey, userId);
        // 数据库
        UserFollow userFollow = new UserFollow();
        userFollow.setFollowUserId(userId);
        userFollow.setBeFollowedUserId(userBeFollowId);
        userFollow.setCreateTime(new Date());
        userMapper.insertUserFollowInfo(userFollow);

    }

    @Override
    public void unfollowUser(UserUnfollowDTO userUnfollowDTO) {
        Integer userId = userUnfollowDTO.getUserId();
        Integer userBeFollowId = userUnfollowDTO.getUserBeFollowId();
        if(Objects.equals(userId, userBeFollowId)) {
            throw new DescribeException(ErrorCode.USER_UNFOLLOW_ONE_PERSON_ERROR.getCode(), ErrorCode.USER_UNFOLLOW_ONE_PERSON_ERROR.getMsg());
        }
        Integer userBeFollowIdCompare = userMapper.selectUserBeFollowedIdByUserId(userId);
        if(userBeFollowIdCompare == null) {
            throw new DescribeException(ErrorCode.USER_UNFOLLOW_NO_ERROR.getCode(), ErrorCode.USER_UNFOLLOW_NO_ERROR.getMsg());
        }
        // 删除缓存数据
        String userFollowKey = RedisKeyUtil.getUserFollowKey(userId);
        redisTemplate.opsForSet().remove(userFollowKey, userBeFollowId);
        String userBeFollowedKey = RedisKeyUtil.getUserBeFollowedKey(userBeFollowId);
        redisTemplate.opsForSet().remove(userBeFollowedKey, userId);
        // 删除数据库数据
        UserFollow userFollow = new UserFollow();
        userFollow.setFollowUserId(userId);
        userFollow.setBeFollowedUserId(userBeFollowId);
        userMapper.deleteFollowInfo(userFollow);
    }

    @Override
    public Integer followState(UserFollowDTO userFollowDTO) {
        Integer userId = userFollowDTO.getUserId();
        Integer userBeFollowId = userFollowDTO.getUserBeFollowId();
        if(Objects.equals(userId, userBeFollowId)) {
            throw new DescribeException(ErrorCode.USER_FOLLOW_ONE_PERSON_ERROR.getCode(), ErrorCode.USER_FOLLOW_ONE_PERSON_ERROR.getMsg());
        }
        String userFollowKey = RedisKeyUtil.getUserFollowKey(userId);
        return redisTemplate.opsForSet().isMember(userFollowKey, userBeFollowId) ? 1 : 0;
    }

}
