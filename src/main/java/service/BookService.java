package service;

import model.Book;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(Long id);

    @Transactional
    void createBook(String title, long authorId, String description, long genreId);

    @Transactional
    void updateBook(long id, String title, long authorId, String description, long genreId);

    @Transactional
    void deleteBook(Long id);
}