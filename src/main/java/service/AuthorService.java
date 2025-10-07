package service;

import model.Author;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AuthorService {

    List<Author> getAllAuthors();

    Author getAuthorById(int id);

    @Transactional
    void createAuthor(Author author);

    @Transactional
    void updateAuthor(Author author);

    @Transactional
    void deleteAuthor(int id);
}
