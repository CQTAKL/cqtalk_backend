package com.cqtalk.interceptor;

import com.cqtalk.annotation.LoginRequired;
import com.cqtalk.annotation.SuperAdminOperate;
import com.cqtalk.dao.UserMapper;
import com.cqtalk.enumerate.user.USER_TYPE_ENUM;
import com.cqtalk.util.user.info.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


@Component
public class SuperAdminOperateInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            SuperAdminOperate superAdminOperate = method.getAnnotation(SuperAdminOperate.class);
            if(superAdminOperate != null) {
                Integer userId = hostHolder.getUserId();
                int userType = userMapper.selectTypeById(userId);
                if(userType != USER_TYPE_ENUM.SUPER_ADMINISTRATOR.getType()) {
                    response.setStatus(403);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"code\":\"403\", \"msg\":\"您的权限不足，无法进行此操作\"}");
                    response.getWriter().flush();
                    return false;
                }
            }
        }
        return true;
    }

}
