package service;

import exception.BookDoesNotExistException;
import model.Book;

import java.util.List;

public interface BookService {
    List<Book> readAllBooks();
    Book getBookById(int id) throws BookDoesNotExistException;
    void createBook(String title, String author, String description);
    void updateBookTitle(int id, String newTitle) throws BookDoesNotExistException;
    void updateBookAuthor(int id, String newAuthor) throws BookDoesNotExistException;
    void updateBookDescription(int id, String newDescription) throws BookDoesNotExistException;
    void updateBook(int id, String newTitle, String newAuthor, String newDescription) throws BookDoesNotExistException;
    void deleteBook(int id) throws BookDoesNotExistException;
    void writeChangesToFile();
}
