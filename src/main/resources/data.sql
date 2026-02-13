INSERT INTO author (first_name, last_name) VALUES
    ('George', 'Orwell'),
    ('Jane', 'Austen'),
    ('Frank', 'Herbert');

INSERT INTO genre (name) VALUES
    ('Dystopian'),
    ('Romance'),
    ('Science Fiction');

INSERT INTO book (title, description) VALUES
    ('1984', 'A novel about a totalitarian future.'),
    ('Pride and Prejudice', 'A classic novel of manners and marriage.'),
    ('Dune', 'A science fiction epic set on the desert planet Arrakis.');

INSERT INTO book_author (book_id, author_id) VALUES
    (1, 1),
    (2, 2),
    (3, 3);

INSERT INTO book_genre (book_id, genre_id) VALUES
    (1, 1),
    (2, 2),
    (3, 3);