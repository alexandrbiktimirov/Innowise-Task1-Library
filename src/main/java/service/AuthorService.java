package service;

import model.Author;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AuthorService {

    List<Author> getAllAuthors();

    Author getAuthorById(long id);

    @Transactional
    void createAuthor(String firstName, String lastName);

    @Transactional
    void updateAuthor(long id, String firstName, String lastName);

    @Transactional
    void deleteAuthor(int id);
}
