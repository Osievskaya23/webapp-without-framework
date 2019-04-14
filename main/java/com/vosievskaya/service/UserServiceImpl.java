package com.vosievskaya.service;

import com.vosievskaya.annotation.Table;
import com.vosievskaya.dao.UserDao;
import com.vosievskaya.model.User;

import java.util.Optional;

import static com.vosievskaya.util.Util.generateToken;
import static com.vosievskaya.util.Util.sha256;

public class UserServiceImpl implements UserService{

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> authorize(User user) {
        return Optional.ofNullable(userDao.authoritze(user));
    }

    @Override
    @Table(name = "USERS")
    public Optional<User> addUser(User user) {
        String hashedPassword = sha256(user.getPassword());
        user.setPassword(hashedPassword);
        user.setToken(generateToken());
        return Optional.ofNullable(userDao.create(user, "USERS"));
    }

    @Override
    public Optional<User> findByToken(String token) {
        return Optional.ofNullable(userDao.getByToken(token));
    }
}
