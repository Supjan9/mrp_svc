package org.mrp.controller;

import com.sun.net.httpserver.HttpExchange;
import org.mrp.controller.Router;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UserController {
    private static final int STATUS_OK = 200;
    private static final String PARAM_USER_ID = "userId";

    private static final String ROUTE_LOGIN = "/users/login";
    private static final String ROUTE_REGISTER = "/users/register";
    private static final String ROUTE_PROFILE = "/users/{userId}/profile";
    private static final String ROUTE_RATINGS = "/users/{userId}/ratings";
    private static final String ROUTE_FAVORITES = "/users/{userId}/favorites";
    private static final String ROUTE_RECOMMENDATIONS = "/users/{userId}/recommendations";

    public void registerRoutes(Router router) {
        router.post(ROUTE_LOGIN, this::loginUser);
        router.post(ROUTE_REGISTER, this::registerUser);
        router.put(ROUTE_PROFILE, this::updateUser);
        router.get(ROUTE_PROFILE, this::getUser);
        router.get(ROUTE_RATINGS, this::getRatings);
        router.get(ROUTE_FAVORITES, this::getFavorites);
        router.get(ROUTE_RECOMMENDATIONS, this::getRecommendations);
    }

    private void loginUser(HttpExchange exchange, Map<String, String> params) throws IOException {
        sendResponse(exchange, STATUS_OK, "User logged in");
    }

    private void registerUser(HttpExchange exchange, Map<String, String> params) throws IOException {
        sendResponse(exchange, STATUS_OK, "User registered");
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

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}