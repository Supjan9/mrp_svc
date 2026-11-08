package org.mrp.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class Controller implements HttpHandler {
    private static final int STATUS_OK = 200;
    private static final int STATUS_NOT_FOUND = 404;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String routeKey = method + " " + path;
        System.out.println(method);
        System.out.println(path);

        switch (routeKey) {
            case "POST /users/login":
                loginUser();
                break;
            case "POST /users/register":
                registerUser();
                break;
            case "PUT /users/{userId}/profile":
                updateUser();
                break;
            case "GET /users/{userId}/profile":
                getUser();
                break;
            case "GET /users/{userId}/ratings":
                getRatings();
                break;
            case "GET /users/{userId}/favorites":
                getFavorites();
                break;
            case "GET /users/{userId}/recommendations":
                getRecommendations();
                break;

        }

    }

    private void getRecommendations() {
        
    }

    private void getFavorites() {

    }

    private void getRatings() {

    }

    private void updateUser() {

    }

    private void getUser() {

    }

    private void loginUser() {

    }

    private void registerUser() {

    }

    private void getHome(HttpExchange exchange) throws IOException {
        sendResponse(exchange, STATUS_OK, "HOMEPAGE");
        System.out.println("homepage accessed");
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());

        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
