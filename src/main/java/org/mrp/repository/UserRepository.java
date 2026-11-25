package org.mrp.repository;

import org.mrp.config.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    private static final String SQL_INSERT_USER = "INSERT INTO users (username, password, token, email) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_TOKEN = "UPDATE users SET token = ? WHERE username = ?";
    private static final String SQL_FIND_BY_TOKEN = "SELECT username FROM users WHERE token = ?";
    private static final String COLUMN_USERNAME = "username";
    private static final String ERROR_CREATE_USER = "Error creating user";
    private static final String ERROR_UPDATE_TOKEN = "Error updating token";
    private static final String ERROR_FIND_USER = "Error finding user by token";

    private final DatabaseManager databaseManager;

    public UserRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }


    public void createUser(String username, String passwordHash, String token, String email) {
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_INSERT_USER)) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            statement.setString(3, token);
            statement.setString(4, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_CREATE_USER, e);
        }
    }

    public void updateToken(String username, String token) {
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_TOKEN)) {
            statement.setString(1, token);
            statement.setString(2, username);
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_UPDATE_TOKEN, e);
        }
    }

    public String findUsernameByToken(String token) {
        try (Connection conn = databaseManager.getConnection();
             PreparedStatement statement = conn.prepareStatement(SQL_FIND_BY_TOKEN)) {
            statement.setString(1, token);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(COLUMN_USERNAME);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_FIND_USER, e);
        }
    }


}
