package org.mrp.controller;

import com.sun.net.httpserver.HttpExchange;
import org.mrp.security.AuthChecker;
import org.mrp.service.MediaService;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

public class MediaController {
    private final MediaService mediaService;
    private final ObjectMapper objectMapper;
    private final AuthChecker authChecker;

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

    private void createMedia(HttpExchange exchange, Map<String, String> stringStringMap) {

    }

    private void deleteMedia(HttpExchange exchange, Map<String, String> stringStringMap) {
    }

    private void updateMedia(HttpExchange exchange, Map<String, String> stringStringMap) {
    }

    private void getMedia(HttpExchange exchange, Map<String, String> stringStringMap) {
    }


    private void getAllMedia(HttpExchange exchange, Map<String, String> stringStringMap) {

    }


}
