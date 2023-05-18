package com.cqtalk.interceptor;

import com.cqtalk.dao.UserMapper;
import com.cqtalk.entity.user.LoginTicket;
import com.cqtalk.enumerate.user.LOGIN_STATUS_ENUM;
import com.cqtalk.service.toc.user.SignLoginService;
import com.cqtalk.util.jwt.JWTUtil;
import com.cqtalk.util.user.info.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {

    @Autowired
    private SignLoginService signLoginService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if(!token.equals("-1")) {
            JWTUtil.verifyToken(token); // 主要是验证会不会过期
        }

         //从cookie中获取凭证
        //String ticket = CookieUtil.getValue(request, "ticket");


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // hostHolder.clear();
        /*SecurityContextHolder.clearContext();*/
    }
}
