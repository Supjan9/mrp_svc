package org.mrp.service;

import org.mrp.repository.MediaRepository;

import java.sql.SQLException;
import java.util.List;

public class MediaService {
    private final MediaRepository mediaRepository;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }

    public void createMedia(String title, String description, String mediaType, Integer releaseYear, List<String> genres, Integer ageRestriction, Long id) throws SQLException {
        mediaRepository.createMedia(title, description, mediaType, releaseYear, genres, ageRestriction, id);
    }


}
