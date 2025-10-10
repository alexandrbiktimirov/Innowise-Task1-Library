package service;

import model.Author;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AuthorService {

    List<Author> getAllAuthors();

    Author getAuthorById(int id);

    @Transactional
    void createAuthor(String firstName, String lastName);

    @Transactional
    void updateAuthor(String firstName, String lastName);

    @Transactional
    void deleteAuthor(int id);
}
