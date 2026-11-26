package org.mrp.service;

import org.mrp.model.entity.User;
import org.mrp.repository.UserRepository;
import org.mrp.security.PasswordHasher;
import org.mrp.security.TokenGenerator;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;

public class UserService {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final TokenGenerator tokenGenerator;
    private static final int TOKEN_EXPIRATION_MINUTES = 30;


    public UserService(UserRepository userRepository, PasswordHasher passwordHasher, TokenGenerator tokenGenerator) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.tokenGenerator = tokenGenerator;
    }

    public String registerUser(String username, String password, String email) throws SQLException {
        String passwordHash = passwordHasher.hash(password);
        String token = tokenGenerator.generateToken();
        Instant expiresAt = Instant.now().plus(Duration.ofMinutes(TOKEN_EXPIRATION_MINUTES));
        userRepository.createUser(username, passwordHash, token, email, expiresAt);
        return token;
    }

    public boolean isTokenValid(String token) {
        if (token == null) {
            return false;
        }
        return userRepository.findUserByToken(token) != null;
    }

    public String loginUser(String userName, String password) {
        User user = userRepository.findUserByUsername(userName);
        if (user == null || !passwordHasher.verify(user.getPassword(), password)) {
            throw new SecurityException("Invalid login credentials");
        }

        String freshToken = tokenGenerator.generateToken();
        Instant expiresAt = Instant.now().plus(Duration.ofMinutes(TOKEN_EXPIRATION_MINUTES));
        try {
            userRepository.updateToken(userName, freshToken, expiresAt);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return freshToken;
    }

}
