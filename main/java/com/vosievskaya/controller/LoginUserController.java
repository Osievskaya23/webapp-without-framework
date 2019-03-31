package com.vosievskaya.controler;

import com.vosievskaya.web.Cookie;
import com.vosievskaya.web.Request;
import com.vosievskaya.web.ViewModel;
import com.vosievskaya.model.User;
import com.vosievskaya.service.UserService;

import java.text.CollationKey;

public class LoginUserController implements Controller {

    private final UserService userService;

    public LoginUserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ViewModel process(Request req) {
        String username = req.getParam("username")[0];
        String password = req.getParam("password")[0];
        User user = User.of(username, password);

        return  userService.authorize(user)
                .map(u -> ViewModel.of("welcome")
                        .withAttribute("user", u))
                        //.withCookie("Mate_application", user.getToken())
                .orElseGet(() -> ViewModel.of("login").withAttribute("error", "msg"));
    }
}
