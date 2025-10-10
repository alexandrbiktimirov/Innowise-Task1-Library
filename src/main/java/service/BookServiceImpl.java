package service;

import aop.Cached;
import model.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.BookDao;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.findAll();
    }

    @Override
    @Cached
    public Book getBookById(Long id) {
        try {
            return bookDao.findById(id);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Transactional
    @Override
    public void createBook(String title, long authorId, String description, long genreId) {
        Book book = new Book(title, authorId, description, genreId);

        bookDao.create(book);
    }

    @Transactional
    @Override
    public void updateBook(long id, String title, long authorId, String description, long genreId) {
        Book book = getBookById(id);
        book.setTitle(title);
        book.setAuthorId(authorId);
        book.setDescription(description);
        book.setGenreId(genreId);

        bookDao.update(book);
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        bookDao.delete(id);
    }
}