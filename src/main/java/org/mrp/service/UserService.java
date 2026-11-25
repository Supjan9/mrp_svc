package org.mrp.service;

import org.mrp.repository.UserRepository;
import org.mrp.security.PasswordHasher;
import org.mrp.security.TokenGenerator;

import java.sql.SQLException;

public class UserService {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final TokenGenerator tokenGenerator;


    public UserService(UserRepository userRepository, PasswordHasher passwordHasher, TokenGenerator tokenGenerator) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.tokenGenerator = tokenGenerator;
    }

    public String registerUser(String username, String password, String email) throws SQLException {
        String passwordHash = passwordHasher.hash(password);
        String token = tokenGenerator.generateToken();
        userRepository.createUser(username, passwordHash, token, email);
        return token;
    }

    public boolean isTokenValid(String token) {
        if (token == null) {
            return false;
        }
        return userRepository.findUsernameByToken(token) != null;
    }

}
