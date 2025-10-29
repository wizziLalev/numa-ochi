CREATE TABLE series (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    publication_date DATE,
    description TEXT,
    cover_image VARCHAR(255),
    publisher VARCHAR(255),
    isbn VARCHAR(255)
);

CREATE TABLE chapter (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    file_path VARCHAR(255) NOT NULL,
    file_type VARCHAR(50),
    series_id BIGINT REFERENCES series(id)
);

CREATE TABLE volume (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    publication_date DATE,
    description TEXT,
    cover_image VARCHAR(255),
    publisher VARCHAR(255),
    isbn VARCHAR(255),
    series_id BIGINT REFERENCES series(id)
);

CREATE TABLE collection (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE volume_chapter (
    volume_id BIGINT REFERENCES volume(id),
    chapter_id BIGINT REFERENCES chapter(id),
    PRIMARY KEY (volume_id, chapter_id)
);

CREATE TABLE collection_series (
    collection_id BIGINT REFERENCES collection(id),
    series_id BIGINT REFERENCES series(id),
    PRIMARY KEY (collection_id, series_id)
);

CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
