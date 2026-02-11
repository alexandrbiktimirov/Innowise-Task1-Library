package service;

import aop.Cached;
import model.Author;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;
import repository.AuthorDao;

import java.util.List;

public class AuthorService {
    private final AuthorDao authorDao;

    public AuthorService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public List<Author> getAllAuthors() {
        return authorDao.findAll();
    }

    public Author getAuthorById(long id) {
        try {
            return authorDao.findById(id);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Transactional
    public void createAuthor(String firstName, String lastName) {
        Author author = new Author(firstName, lastName);
        authorDao.create(author);
    }

    @Transactional
    public void updateAuthor(long id, String firstName, String lastName) {
        Author author = getAuthorById(id);
        author.setFirstName(firstName);
        author.setLastName(lastName);

        authorDao.update(author);
    }

    @Transactional
    public void deleteAuthor(int id) {
        authorDao.delete(id);
    }
}
