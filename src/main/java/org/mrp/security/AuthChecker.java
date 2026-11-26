package org.mrp.security;

import com.sun.net.httpserver.HttpExchange;
import org.mrp.model.entity.User;
import org.mrp.repository.UserRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class AuthChecker {
    private final UserRepository userRepository;
    private static final String BEARER_PREFIX = "Bearer ";

    public AuthChecker(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(HttpExchange exchange) throws IOException {
        String authHeader = exchange.getRequestHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            sendUnauthorized(exchange);
            return null;
        }
        String token = authHeader.substring(BEARER_PREFIX.length()).trim();
        if (token.isEmpty()) {
            sendUnauthorized(exchange);
            return null;
        }
        User user = userRepository.findUserByToken(token);
        if (user == null) {
            sendUnauthorized(exchange);
            return null;
        }
        return user;
    }

    private void sendUnauthorized(HttpExchange exchange) throws IOException {
        byte[] response = "{\"error\":\"Unauthorized\"}".getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(401, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }
}
