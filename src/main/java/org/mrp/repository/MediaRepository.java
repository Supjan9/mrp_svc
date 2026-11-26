package org.mrp.repository;

import org.mrp.config.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class MediaRepository {
    private static final String SQL_CREATE_MEDIA = "INSERT INTO media (title, description, media, release_year, age_restriction, creator_id) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
    private static final String SQL_INSERT_GENRE = "INSERT INTO genres (media_id, genre) VALUES (?, ?) ON CONFLICT DO NOTHING";
    private static final String COL_ID = "id";
    private static final String ERR_NO_ID = "Creating media failed, no ID obtained.";

    private final DatabaseManager databaseManager;

    public MediaRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public long createMedia(String title, String description, String mediaType, Integer releaseYear, List<String> genres, Integer ageRestriction, Long id) throws SQLException {
        try (Connection conn = databaseManager.getConnection()) {
            conn.setAutoCommit(false);
            try {
                long mediaId = insertMedia(conn, title, description, mediaType, releaseYear, ageRestriction, id);
                insertGenres(conn, mediaId, genres);
                conn.commit();
                return mediaId;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    private long insertMedia(Connection conn, String title, String description, String mediaType, Integer releaseYear, Integer ageRestriction, Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(SQL_CREATE_MEDIA)) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setObject(3, mediaType, Types.OTHER);
            stmt.setInt(4, releaseYear);
            stmt.setInt(5, ageRestriction);
            stmt.setLong(6, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException(ERR_NO_ID);
                }
                return rs.getLong(COL_ID);
            }
        }
    }

    private void insertGenres(Connection conn, long mediaId, List<String> genres) throws SQLException {
        if (genres == null || genres.isEmpty()) {
            return;
        }

        try (PreparedStatement stmt = conn.prepareStatement(SQL_INSERT_GENRE)) {
            for (String genre : genres) {
                if (genre == null || genre.isBlank()) {
                    continue;
                }
                stmt.setLong(1, mediaId);
                stmt.setString(2, genre.trim());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }
}