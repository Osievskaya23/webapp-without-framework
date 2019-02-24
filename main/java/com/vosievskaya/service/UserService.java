package com.vosievskaya.service;

import com.vosievskaya.model.Category;
import com.vosievskaya.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> authorize(User user);
}
