package service;

import model.Author;
import model.Book;
import model.Genre;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(Long id);

    @Transactional
    void createBook(String title, Author author, String description, Genre genre);

    @Transactional
    void updateBook(long id, String title, Author author, String description, Genre genre);

    @Transactional
    void deleteBook(Long id);
}