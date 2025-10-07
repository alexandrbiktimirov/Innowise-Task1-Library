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
    public void createBook(Book book) {
        bookDao.create(book);
    }

    @Transactional
    @Override
    public void updateBook(Book book) {
        bookDao.update(book);
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        bookDao.delete(id);
    }
}