ALTER TABLE media
    ADD COLUMN creator_id BIGINT NOT NULL,
    ADD CONSTRAINT fk_media_creator
        FOREIGN KEY (creator_id)
            REFERENCES users (id)
            ON DELETE CASCADE;