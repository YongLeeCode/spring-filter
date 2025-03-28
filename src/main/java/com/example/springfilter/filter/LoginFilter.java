package com.example.springfilter.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

/**
 * @author : yong
 * @packageName : com.example.springfilter.filter
 * @fileName : LoginFilter
 * @date : 3/27/25
 * @description :
 */
@Slf4j
public class LoginFilter implements Filter {
    private static final String[] WHITE_LIST = {"/", "/user/signup", "/login", "logout"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        log.info("ㄹㅗ그인 필터 로직 실행");
        if (!isWhiteList(requestURI)) {
            HttpSession session = httpRequest.getSession(false);
            if (session == null || session.getAttribute("sessionKey") == null) {
                throw new RuntimeException("로그인 해야합니다.");
            }
            log.info("로그인 성공");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
