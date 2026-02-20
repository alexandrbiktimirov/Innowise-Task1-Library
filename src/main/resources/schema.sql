CREATE TABLE author (
    id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name  TEXT NOT NULL
);

CREATE TABLE genre (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE book (
    id SERIAL PRIMARY KEY,
    title TEXT,
    description TEXT
);

CREATE TABLE book_author (
    book_id   INT NOT NULL REFERENCES book(id)   ON DELETE CASCADE,
    author_id INT NOT NULL REFERENCES author(id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, author_id)
);

CREATE TABLE book_genre (
    book_id  INT NOT NULL REFERENCES book(id)  ON DELETE CASCADE,
    genre_id INT NOT NULL REFERENCES genre(id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, genre_id)
);

DROP TABLE IF EXISTS book_genre;
DROP TABLE IF EXISTS book_author;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS genre;
DROP TABLE IF EXISTS author;