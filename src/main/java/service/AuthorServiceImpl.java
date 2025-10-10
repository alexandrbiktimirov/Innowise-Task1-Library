package service;

import aop.Cached;
import model.Author;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.AuthorDao;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.findAll();
    }

    @Override
    @Cached
    public Author getAuthorById(int id) {
        try {
            return authorDao.findById(id);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Transactional
    @Override
    public void createAuthor(String firstName, String lastName) {
        Author author = new Author(firstName, lastName);
        authorDao.create(author);
    }

    @Transactional
    @Override
    public void updateAuthor(String firstName, String lastName) {
        Author author = new Author(firstName, lastName);

        authorDao.update(author);
    }

    @Transactional
    @Override
    public void deleteAuthor(int id) {
        authorDao.delete(id);
    }
}
