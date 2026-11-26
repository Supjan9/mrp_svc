package org.mrp.controller;

import com.sun.net.httpserver.HttpExchange;
import org.mrp.model.dto.UserCredentialsDTO;
import org.mrp.model.dto.UserRegisterDTO;
import org.mrp.security.AuthChecker;
import org.mrp.service.UserService;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public class UserController {


    private final UserService userService;
    private final AuthChecker authChecker;
    private final ObjectMapper objectMapper;


    private static final int STATUS_OK = 200;
    private static final int STATUS_CREATED = 201;
    private static final int STATUS_BAD_REQUEST = 400;
    private static final int STATUS_CONFLICT = 409;
    private static final int STATUS_INTERNAL_ERROR = 500;
    private static final String PARAM_USER_ID = "userId";
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";

    private static final String JSON_KEY_MESSAGE = "message";
    private static final String JSON_KEY_TOKEN = "token";
    private static final String MSG_INVALID_JSON = "Invalid JSON";
    private static final String MSG_MISSING_FIELDS = "Missing fields";
    private static final String MSG_USER_REGISTER_FAILED = "User could not be registered";
    private static final String MSG_USER_LOGIN_FAILED = "Login failed";
    private static final String MSG_SERVER_ERROR = "Internal server error";

    private static final String MSG_USER_REGISTERED = "User registered successfully";


    public UserController(UserService userService, AuthChecker authChecker, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.authChecker = authChecker;
    }

    public void registerRoutes(Router router) {
        router.post("/users/login", this::loginUser);
        router.post("/users/register", this::registerUser);
        router.put("/users/{userId}/profile", this::updateUser);
        router.get("/users/{userId}/profile", this::getUser);
        router.get("/users/{userId}/ratings", this::getRatings);
        router.get("/users/{userId}/favorites", this::getFavorites);
        router.get("/users/{userId}/recommendations", this::getRecommendations);
    }

    private void loginUser(HttpExchange exchange, Map<String, String> params) throws IOException {
        UserCredentialsDTO userCredentialsDTO;
        try {
            userCredentialsDTO = objectMapper.readValue(exchange.getRequestBody(), UserCredentialsDTO.class);
        } catch (JacksonException e) {
            sendResponse(exchange, STATUS_BAD_REQUEST, Map.of(JSON_KEY_MESSAGE, MSG_INVALID_JSON));
            return;
        }
        if (userCredentialsDTO.getUsername() == null || userCredentialsDTO.getPassword() == null) {
            sendResponse(exchange, STATUS_BAD_REQUEST, Map.of(JSON_KEY_MESSAGE, MSG_MISSING_FIELDS));
            return;
        }
        try {
            String newToken = userService.loginUser(
                    userCredentialsDTO.getUsername(),
                    userCredentialsDTO.getPassword()
            );
            sendResponse(exchange, STATUS_OK, Map.of(JSON_KEY_TOKEN, newToken));
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, STATUS_CONFLICT, Map.of(JSON_KEY_MESSAGE, MSG_USER_LOGIN_FAILED));
        }
    }

    private void registerUser(HttpExchange exchange, Map<String, String> params) throws IOException {
        UserRegisterDTO userRegisterDTO;
        try {
            userRegisterDTO = objectMapper.readValue(exchange.getRequestBody(), UserRegisterDTO.class);
        } catch (JacksonException e) {
            sendResponse(exchange, STATUS_BAD_REQUEST, Map.of(JSON_KEY_MESSAGE, MSG_INVALID_JSON));
            return;
        }

        if (userRegisterDTO.getUsername() == null ||
                userRegisterDTO.getEmail() == null ||
                userRegisterDTO.getPassword() == null) {
            sendResponse(exchange, STATUS_BAD_REQUEST, Map.of(JSON_KEY_MESSAGE, MSG_MISSING_FIELDS));
            return;
        }
        try {
            userService.registerUser(
                    userRegisterDTO.getUsername(),
                    userRegisterDTO.getPassword(),
                    userRegisterDTO.getEmail()
            );
            sendResponse(exchange, STATUS_CREATED, Map.of(JSON_KEY_MESSAGE, MSG_USER_REGISTERED));
        } catch (SQLException e) {
            sendResponse(exchange, STATUS_CONFLICT, Map.of(JSON_KEY_MESSAGE, MSG_USER_REGISTER_FAILED));
        } catch (RuntimeException e) {
            sendResponse(exchange, STATUS_INTERNAL_ERROR, Map.of(JSON_KEY_MESSAGE, MSG_SERVER_ERROR));
        }
    }

    private void getUser(HttpExchange exchange, Map<String, String> params) throws IOException {
        String userId = params.get(PARAM_USER_ID);
        sendResponse(exchange, STATUS_OK, "Profile for user " + userId);
    }

    private void updateUser(HttpExchange exchange, Map<String, String> params) throws IOException {
        String userId = params.get(PARAM_USER_ID);
        sendResponse(exchange, STATUS_OK, "Updated profile for user " + userId);
    }

    private void getRatings(HttpExchange exchange, Map<String, String> params) throws IOException {
        String userId = params.get(PARAM_USER_ID);
        sendResponse(exchange, STATUS_OK, "Ratings for user " + userId);
    }

    private void getFavorites(HttpExchange exchange, Map<String, String> params) throws IOException {
        String userId = params.get(PARAM_USER_ID);
        sendResponse(exchange, STATUS_OK, "Favorites for user " + userId);
    }

    private void getRecommendations(HttpExchange exchange, Map<String, String> params) throws IOException {
        String userId = params.get(PARAM_USER_ID);
        sendResponse(exchange, STATUS_OK, "Recommendations for user " + userId);
    }

    private void sendResponse(HttpExchange exchange, int statusCode, Object response) throws IOException {
        byte[] responseBytes = objectMapper.writeValueAsBytes(response);
        exchange.getResponseHeaders().set(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}