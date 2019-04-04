package com.vosievskaya.web;

import com.vosievskaya.ConnectionFactory;
import com.vosievskaya.model.User;
import com.vosievskaya.service.UserService;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class UserFilter implements Filter {

    private Set<String> openUri = new HashSet<>();

    private static UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        openUri.add("/servlet/login");
        openUri.add("/servlet/register");
        userService = ConnectionFactory.getUserServiceImpl(ConnectionFactory.getUserDao(ConnectionFactory.getConnection()));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Cookie[] cookies = req.getCookies();

        if (openUri.contains(req.getRequestURI())) {
            processRequest(request, response, chain);
        } else {
            Optional<User> user = Stream.of(cookies)
                    .filter(c -> c.getName().equals("Mate_Application"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .flatMap(userService::findByToken);

            if (user.isPresent()) {
                processRequest(request, response, chain);
            } else {
                dispatch(request, response, "login");
            }
            processRequest(request, response, chain);
        }
    }

    @Override
    public void destroy() {

    }

    private void dispatch(ServletRequest request, ServletResponse response, String viewName) throws ServletException, IOException {
        request.getRequestDispatcher(String.format("/WEB-INF/views/%s.jsp", viewName)).forward(request, response);
    }

    private void processRequest(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }
}
