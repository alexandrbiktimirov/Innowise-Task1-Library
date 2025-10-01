CREATE TABLE author (
    id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL
);

CREATE TABLE genre (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE book (
    id SERIAL PRIMARY KEY,
    title TEXT,
    author_id INT NOT NULL REFERENCES author(id),
    genre_id INT NOT NULL REFERENCES genre(id)
);