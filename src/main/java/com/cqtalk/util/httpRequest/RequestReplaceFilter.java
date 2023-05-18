package com.cqtalk.util.httpRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 替换Request对象
 */
@Component
public class RequestReplaceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String[] uris = {"/post/add/picture", "/info/modifyHeaderUrl", "/file/upload"};
        int flag = 0;
        for (String uri : uris) {
            if(request.getRequestURI().equals(uri)) {
                flag = 1;
            }
        }
        if (flag != 1) {
            if (!(request instanceof MyServletRequestWrapper)) {
                request = new MyServletRequestWrapper(request);
            }
        }
        filterChain.doFilter(request, response);
    }
}
