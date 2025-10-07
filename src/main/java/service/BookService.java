package service;

import model.Book;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(Long id);

    @Transactional
    void createBook(Book book);

    @Transactional
    void updateBook(Book book);

    @Transactional
    void deleteBook(Long id);
}