CREATE TABLE media
(
    id             BIGSERIAL PRIMARY KEY,
    title          VARCHAR(255) NOT NULL,
    description    TEXT,
    media          VARCHAR(255) NOT NULL,
    releaseYear    INT          NOT NULL,
    ageRestriction INT,
    createdAt      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP

);

CREATE TABLE genres
(
    media_id BIGINT       NOT NULL REFERENCES media (id) ON DELETE CASCADE,
    genre    VARCHAR(255) NOT NULL,
    PRIMARY KEY (media_id, genre)
)