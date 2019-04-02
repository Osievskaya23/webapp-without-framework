package com.vosievskaya.dao;

import com.vosievskaya.model.Role;
import com.vosievskaya.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.vosievskaya.util.Util.sha256;

public class UserDao extends AbstractDao<User, Long>{

    private Connection connection;

    public UserDao(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    public User authoritze(User user) {
        String query = "SELECT * FROM USERS WHERE USERNAME=? AND PASSWORD=?";

        PreparedStatement statement;
        ResultSet rs;
        User result = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(1, sha256(user.getPassword()));

            rs = statement.executeQuery();

            if (rs.next()) {
                result = getUserFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

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

        PreparedStatement statement;
        ResultSet resultSet;
        User result = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, token);
            resultSet = statement.executeQuery();

            result = getUserWithRolesFromResultSet(resultSet);

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
}
