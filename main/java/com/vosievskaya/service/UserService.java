package com.vosievskaya.service;

import com.vosievskaya.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> authorize(User user);

    Optional<User> addUser(User user);

    Optional<User> findByToken(String token);
}
