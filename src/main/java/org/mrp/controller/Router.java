package org.mrp.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Router implements HttpHandler {
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_PUT = "PUT";
    private static final String METHOD_DELETE = "DELETE";
    private static final int STATUS_NOT_FOUND = 404;
    private static final String MESSAGE_NOT_FOUND = "Route not found";

    private final List<Route> routes = new ArrayList<>();

    public void get(String path, RouteHandler handler) {
        routes.add(new Route(METHOD_GET, path, handler));
    }

    public void post(String path, RouteHandler handler) {
        routes.add(new Route(METHOD_POST, path, handler));
    }

    public void put(String path, RouteHandler handler) {
        routes.add(new Route(METHOD_PUT, path, handler));
    }

    public void delete(String path, RouteHandler handler) {
        routes.add(new Route(METHOD_DELETE, path, handler));
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        for (Route route : routes) {
            if (!route.matchesMethod(method)) {
                continue;
            }

            Map<String, String> params = route.matchPath(path);
            if (params != null) {
                route.getHandler().handle(exchange, params);
                return;
            }
        }

        sendNotFound(exchange);
    }

    private void sendNotFound(HttpExchange exchange) throws IOException {
        byte[] responseBytes = MESSAGE_NOT_FOUND.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(STATUS_NOT_FOUND, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}