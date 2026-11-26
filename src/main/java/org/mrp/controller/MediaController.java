package org.mrp.controller;

import com.sun.net.httpserver.HttpExchange;
import org.mrp.model.dto.MediaInputDTO;
import org.mrp.model.entity.User;
import org.mrp.security.AuthChecker;
import org.mrp.service.MediaService;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

public class MediaController {

    private final MediaService mediaService;
    private final ObjectMapper objectMapper;
    private final AuthChecker authChecker;

    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String JSON_KEY_MESSAGE = "message";
    private static final String JSON_KEY_TOKEN = "token";
    private static final String MSG_INVALID_JSON = "Invalid JSON";
    private static final String MSG_MISSING_FIELDS = "Missing fields";
    private static final String MSG_SERVER_ERROR = "Internal server error";
    private static final String MSG_MEDIA_CREATED = "Media successfully created";
    private static final String MSG_MEDIA_CREATE_FAILED = "Media could not be created";


    public MediaController(MediaService mediaService, AuthChecker authChecker, ObjectMapper objectMapper) {
        this.mediaService = mediaService;
        this.objectMapper = objectMapper;
        this.authChecker = authChecker;
    }

    public void registerRoutes(Router router) {
        router.get("/media", this::getAllMedia);
        router.post("/media", this::createMedia);
        router.get("/media/{mediaId}", this::getMedia);
        router.put("/media/{mediaId}", this::updateMedia);
        router.delete("/media/{mediaId}", this::deleteMedia);
    }

    private void createMedia(HttpExchange exchange, Map<String, String> stringStringMap) throws IOException {
        User currentUser = authChecker.authenticate(exchange);
        if (currentUser == null) {
            System.out.println("user coldnt be authenticated");
            return;
        }
        MediaInputDTO mediaInputDTO;
        try {
            mediaInputDTO = objectMapper.readValue(exchange.getRequestBody(), MediaInputDTO.class);
        } catch (JacksonException e) {
            sendResponse(exchange, 400, Map.of(JSON_KEY_MESSAGE, MSG_INVALID_JSON));
            return;
        }

        if (mediaInputDTO.getTitle() == null ||
                mediaInputDTO.getMediaType() == null ||
                mediaInputDTO.getReleaseYear() == null ||
                mediaInputDTO.getAgeRestriction() == null) {
            sendResponse(exchange, 400, Map.of(JSON_KEY_MESSAGE, MSG_MISSING_FIELDS));
            return;
        }
        try {
            mediaService.createMedia(
                    mediaInputDTO.getTitle(),
                    mediaInputDTO.getDescription(),
                    mediaInputDTO.getMediaType(),
                    mediaInputDTO.getReleaseYear(),
                    mediaInputDTO.getGenres(),
                    mediaInputDTO.getAgeRestriction(),
                    currentUser.getId()
            );
            sendResponse(exchange, 201, Map.of(JSON_KEY_MESSAGE, MSG_MEDIA_CREATED));
        } catch (SQLException e) {
            sendResponse(exchange, 409, Map.of(JSON_KEY_MESSAGE, MSG_MEDIA_CREATE_FAILED));
        } catch (Exception e) {
            sendResponse(exchange, 500, Map.of(JSON_KEY_MESSAGE, MSG_SERVER_ERROR));
        }


    }

    private void deleteMedia(HttpExchange exchange, Map<String, String> stringStringMap) {
    }

    private void updateMedia(HttpExchange exchange, Map<String, String> stringStringMap) {
    }

    private void getMedia(HttpExchange exchange, Map<String, String> stringStringMap) {
    }


    private void getAllMedia(HttpExchange exchange, Map<String, String> stringStringMap) {

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
