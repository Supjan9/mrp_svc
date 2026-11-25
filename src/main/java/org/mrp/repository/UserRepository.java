package org.mrp.repository;

import org.mrp.config.DatabaseManager;

import java.sql.*;
import java.time.Instant;

public class UserRepository {

    private static final String SQL_CREATE_USER = "INSERT INTO users (username, password, token, email, token_expires_at) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_TOKEN = "UPDATE users SET token = ?, token_expires_at = ? WHERE username = ?";
    private static final String SQL_FIND_BY_TOKEN = "SELECT username FROM users WHERE token = ? AND token_expires_at > NOW()";
    private static final String COLUMN_USERNAME = "username";
    private static final String ERROR_CREATE_USER = "Error creating user";
    private static final String ERROR_UPDATE_TOKEN = "Error updating token";
    private static final String ERROR_FIND_USER = "Error finding user by token";

    private final DatabaseManager databaseManager;

    public UserRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }


    public void createUser(String username, String passwordHash, String token, String email, Instant expiresAt) throws SQLException {
        try (Connection conn = databaseManager.getConnection(); PreparedStatement statement = conn.prepareStatement(SQL_CREATE_USER)) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            statement.setString(3, token);
            statement.setString(4, email);
            statement.setTimestamp(5, Timestamp.from(expiresAt));
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw new SQLException(ERROR_CREATE_USER, e);
        }
    }

    public void updateToken(String username, String token, Instant expiresAt) {
        try (Connection conn = databaseManager.getConnection(); PreparedStatement statement = conn.prepareStatement(SQL_UPDATE_TOKEN)) {
            statement.setString(1, token);
            statement.setTimestamp(2, Timestamp.from(expiresAt));
            statement.setString(3, username);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(ERROR_UPDATE_TOKEN, e);
        }
    }

    public String findUsernameByToken(String token) {
        try (Connection conn = databaseManager.getConnection(); PreparedStatement statement = conn.prepareStatement(SQL_FIND_BY_TOKEN)) {
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
