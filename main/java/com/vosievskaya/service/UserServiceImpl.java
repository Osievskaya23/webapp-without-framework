package com.vosievskaya.service;

import com.vosievskaya.DBEmulator;
import com.vosievskaya.dao.UserDao;
import com.vosievskaya.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService{

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> authorize(User user) {
        Optional<User> thisUser = DBEmulator.getUsers().stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findFirst();

        return thisUser.map(User::getPassword)
                .filter(p -> p.equals(sha256(user.getPassword())))
                .flatMap(p -> thisUser);
    }

    @Override
    public Optional<User> addUser(User user) {
        String hashedPassword = sha256(user.getPassword());
        user.setPassword(hashedPassword);
        user.setToken(generateToken());
        return Optional.ofNullable(userDao.addUser(user));
    }

    @Override
    public Optional<User> findByToken(String token) {
        return Optional.ofNullable(userDao.getByToken(token));
    }

    private static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte hash1 : hash) {
                String hex = Integer.toHexString(0xff & hash1);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}
