package com.vosievskaya.dao;

import com.vosievskaya.Factory;
import com.vosievskaya.model.Role;
import com.vosievskaya.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;

public class UserDaoImpl implements UserDao {

    private final Connection connection;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User addUser(User user) {
        String query = "INSERT INTO USERS VALUES(?, ?, ?, ?, ?, ?)";

        PreparedStatement statement;

        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setNull(1, -5);
            statement.setString(2, user.getUsername());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getToken());
            statement.setString(5, user.getFirstName());
            statement.setString(6, user.getSecondName());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()) {
                user.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getByToken(String token) {
        String query = "select u.id as u_id, " +
                "u.username," +
                "u.password, " +
                "u.token, " +
                "u.first_name, " +
                "u.last_name, " +
                "r.id as r_id, " +
                "r.role_name " +
                "from users u " +
                "join users_to_roles utr on u.id = utr.fk_user_id " +
                "join roles r on utr.fk_role_id = r.id " +
                "where u.token = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        User result = null;
        try {
            statement= connection.prepareStatement(query);
            statement.setString(1, token);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private User getUserWithRolesFromResultSet(ResultSet rs) throws SQLException {
        List<Role> roles = new ArrayList<>();
        User result = null;

        if (rs.next()) {
            result = getUserFromResultSet(rs);
            result.setRoles(roles);

            while (!rs.isAfterLast()) {
                roles.add(getRoleFromResultSet(rs));
                rs.next();
            }
        }

        return result;
    }

    private User getUserFromResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getLong("u_id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String token = rs.getString("token");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        return new User(id, username, password, token, firstName, lastName);
    }

    private Role getRoleFromResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getLong("r_id");
        String roleName = rs.getString("role_name");
        return new Role(id, Role.RoleName.valueOf(roleName));
    }

    /*public static void main(String[] args) {
        User user = new User(null,
                "lucky13",
                "12345678",
                "token",
                "Anton",
                "LastName");
        UserDao userDao = new UserDaoImpl(Factory.getConnection());
        User result = userDao.addUser(user);
        System.out.println(result);
    }*/
}
