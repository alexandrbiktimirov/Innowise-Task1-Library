package service;

import model.Author;
import model.Book;
import model.Genre;
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
    public Book getBookById(Long id) {
        try {
            return bookDao.findById(id);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Transactional
    @Override
    public void createBook(String title, Author author, String description, Genre genre) {
        Book book = new Book(title, author, genre, description);

        bookDao.create(book);
    }

    @Transactional
    @Override
    public void updateBook(long id, String title, Author author, String description, Genre genre) {
        Book book = getBookById(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        book.setGenre(genre);

        bookDao.update(book);
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        bookDao.delete(id);
    }
}