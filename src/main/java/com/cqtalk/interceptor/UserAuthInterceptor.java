package com.cqtalk.interceptor;

import com.cqtalk.annotation.AuthRequired;
import com.cqtalk.annotation.LoginRequired;
import com.cqtalk.dao.UserMapper;
import com.cqtalk.util.user.info.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

// 用户实名认证拦截
@Component
public class UserAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            AuthRequired authRequired = method.getAnnotation(AuthRequired.class);
            if (authRequired != null) {
                int id =  hostHolder.getUserId();
                String realName = userMapper.selectRealNameById(id);
                if(realName == null) {
                    response.setStatus(30002);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"code\":\"30002\", \"msg\":\"您尚未实名认证\"}");
                    response.getWriter().flush();
                    return false;
                }
            }
        }
        return true;
    }

}
