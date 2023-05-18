package com.cqtalk.interceptor;

import com.cqtalk.annotation.LoginRequired;
import com.cqtalk.entity.user.LoginTicket;
import com.cqtalk.enumerate.user.LOGIN_STATUS_ENUM;
import com.cqtalk.service.toc.user.SignLoginService;
import com.cqtalk.util.jwt.JWTUtil;
import com.cqtalk.util.user.info.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    // 暂时不用cookie
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private SignLoginService signLoginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            String token = request.getHeader("Authorization");
            /*if (loginRequired != null && hostHolder.getUserId() == null) {
                response.setStatus(30001);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("{\"code\":\"30001\", \"msg\":\"您尚未登录\"}");
                response.getWriter().flush();
                return false;
            }*/
//            Integer userId = jwtUtil.getUserId(token);
            int userId = -1;
            if(loginRequired != null) {
//                userId = Integer.parseInt(StringUtils.substringBefore(token, "_"));
                if(token.equals("-1")) {
                    response.setStatus(30001);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"code\":\"30001\", \"msg\":\"您尚未登录\"}");
                    response.getWriter().flush();
                    return false;
                }
                // 如果能从cookie中获得登录信息的话
                if (token != null && !token.equals("-1")) {
                    // 查询凭证
                    String ticket = jwtUtil.getLoginTicket(token);
                    LoginTicket loginTicket = signLoginService.findLoginTicket(ticket);
                    // 检查凭证是否有效
                    if (loginTicket != null && loginTicket.getStatus() == LOGIN_STATUS_ENUM.LOGIN_SUCCESS.getType() && loginTicket.getExpired().after(new Date())) {
                        // 在本次请求中持有用户id的信息
                        hostHolder.setUserId(loginTicket.getUserId());
                /*// 构建用户认证的结果,并存入SecurityContext,以便于Security进行授权.
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user, user.getPassword(), userService.getAuthorities(user.getId()));
                SecurityContextHolder.setContext(new SecurityContextImpl(authentication));*/
                    }
                }
            }
        }

        return true;
    }

}



