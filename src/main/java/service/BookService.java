package service;

import aop.Cached;
import model.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import repository.BookDao;

import java.util.List;

public class BookService {

    private final BookDao bookDao;

    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    @Cached
    public Book getBookById(Long id) {
        try {
            return bookDao.findById(id);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Transactional
    public void createBook(String title, String description, long authorId, long genreId) {
        Book book = new Book(title, description, authorId, genreId);

        bookDao.create(book);
    }

    @Transactional
    public void updateBook(long id, String title, String description, long genreId, long authorId) {
        Book book = getBookById(id);

        book.setTitle(title);
        book.setAuthorId(authorId);
        book.setDescription(description);
        book.setGenreId(genreId);

        bookDao.update(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        bookDao.delete(id);
    }
}