package com.vosievskaya.dao;

import com.vosievskaya.model.User;

public interface UserDao {

    User addUser(User user);

    User getByToken(String token);
}
