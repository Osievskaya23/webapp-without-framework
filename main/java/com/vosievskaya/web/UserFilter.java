package com.vosievskaya.controler;

import com.vosievskaya.Factory;
import com.vosievskaya.service.UserService;

import javax.servlet.*;
import java.io.IOException;

public class UserFilter implements Filter {

    private static UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userService = Factory.getUserServiceImpl();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
