package com.cqtalk.aop;

import com.alibaba.fastjson.JSONObject;
import com.cqtalk.dao.CommentMapper;
import com.cqtalk.dao.FileMapper;
import com.cqtalk.dao.PostMapper;
import com.cqtalk.dao.UserMapper;
import com.cqtalk.enumerate.entityType.ENTITY_TYPE_ENUM;
import com.cqtalk.enumerate.user.USER_TYPE_ENUM;
import com.cqtalk.util.returnObject.DescribeException;
import com.cqtalk.util.returnObject.ErrorCode;
import com.cqtalk.util.user.info.HostHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Aspect
@Component
public class UserRequiredAspect {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private HostHolder hostHolder;

    @Pointcut("@annotation(com.cqtalk.aop.UserRequired)")
    public void test() {

    }

    /*@Around("test() && @annotation(userRequired)")*/
    /*public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object[] args = proceedingJoinPoint.getArgs();



        Long id = Long.parseLong(request.getHeader("id"));
        String URI = request.getRequestURI();
        String subURI = URI.substring(1, URI.indexOf("/", 1));
        System.out.println("subURI: " + subURI);
        // 后期通过泛型表示
        int compareUserId = 0;
        if(subURI.equals("file")) {
            compareUserId = fileMapper.selectUserIdById(id);
        }
        if(subURI.equals("post")) {

        }
        if(subURI.equals("comment")) {
            compareUserId = commentMapper.selectUserIdById(id);
        }
        int userId = hostHolder.getUserId();
        int userType = userMapper.selectTypeById(userId);
        if(userId != compareUserId && userType != USER_TYPE_ENUM.SUPER_ADMINISTRATOR.getType()) {
            throw new DescribeException(ErrorCode.USER_NO_PERMISSION_ERROR.getCode(), ErrorCode.USER_NO_PERMISSION_ERROR.getMsg());
        }
        return proceedingJoinPoint.proceed(args);
    }*/

//    @Around("test() && @annotation(userRequired)")
    @Before("test() && @annotation(userRequired)")
    public void action(UserRequired userRequired) throws Throwable {
        int type = userRequired.entity();
        String info = userRequired.name();
        // 注意：此处只能传入id，因为要转成Long
        String i = getRequestBodyInfo(info);
        long id = Long.parseLong(i);
        int compareUserId = 0;
        if(type == ENTITY_TYPE_ENUM.FILE.getType()) {
            compareUserId = fileMapper.selectUserIdById(id);
        } else if(type == ENTITY_TYPE_ENUM.POST.getType()) {
            compareUserId = postMapper.selectUserIdById(id);
        } else if(type == ENTITY_TYPE_ENUM.COMMENT.getType()) {
            compareUserId = commentMapper.selectUserIdById(id);
        } else if(type == ENTITY_TYPE_ENUM.FILE_RECOMMEND.getType()) {
            compareUserId = fileMapper.selectUserIdByFileId(id);
        }
        // TODO: 此处进行信息补充
        int userId = hostHolder.getUserId();
        int userType = userMapper.selectTypeById(userId);
        if(userId != compareUserId && userType != USER_TYPE_ENUM.SUPER_ADMINISTRATOR.getType()) {
            throw new DescribeException(ErrorCode.USER_NO_PERMISSION_ERROR.getCode(), ErrorCode.USER_NO_PERMISSION_ERROR.getMsg());
        }
    }


    public String ReadAsChars() {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public String getRequestBodyInfo(String name) {
        String reqBody = ReadAsChars();
        JSONObject json = JSONObject.parseObject(reqBody);
        return json.getString(name);
    }

}
