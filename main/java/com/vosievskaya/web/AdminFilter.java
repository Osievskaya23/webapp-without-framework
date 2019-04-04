package com.vosievskaya.web;

import static com.vosievskaya.Factory.getConnection;
import static com.vosievskaya.Factory.getUserDaoImpl;
import static com.vosievskaya.Factory.getUserServiceImpl;

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

import com.vosievskaya.model.Role;
import com.vosievskaya.model.User;
import com.vosievskaya.service.UserService;

public class AdminFilter implements Filter {

    private Set<String> openUri = new HashSet<>();

    private static UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        openUri.add("/servlet/login");
        openUri.add("/servlet/register");
        userService = getUserServiceImpl(getUserDaoImpl(getConnection()));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        javax.servlet.http.Cookie[] cookies = req.getCookies();

        if (openUri.contains(req.getRequestURI())) {
            processRequest(request, response, chain);
        } else {
            Optional<User> user = Stream.of(cookies)
                    .filter(c -> c.getName().equals("Mate_Application"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .flatMap(userService::findByToken);

            if (req.getRequestURI().startsWith("/servlet/admin")) {
                boolean isAuthorized = user.map(u -> u.getRoles().stream()
                        .anyMatch(r -> r.getRoleName().equals(Role.RoleName.ADMIN)))
                        .orElse(false);

                if (isAuthorized) {
                    processRequest(request, response, chain);
                } else {
                    dispatch(request, response, "notAllowed");
                }

            } else {
                if (user.isPresent()) {
                    processRequest(request, response, chain);
                } else {
                    dispatch(request, response, "login");
                }
            }
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
