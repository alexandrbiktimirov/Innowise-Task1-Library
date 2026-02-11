package service;

import dto.AuthorDto;
import exception.AuthorDoesNotExistException;
import mapper.LibraryMapper;
import model.Author;
import org.springframework.transaction.annotation.Transactional;
import repository.AuthorDao;

import java.util.List;
import java.util.Optional;

public class AuthorService {
    private final AuthorDao authorDao;
    private final LibraryMapper libraryMapper;

    public AuthorService(AuthorDao authorDao, LibraryMapper libraryMapper) {
        this.authorDao = authorDao;
        this.libraryMapper = libraryMapper;
    }

    @Transactional(readOnly = true)
    public List<AuthorDto> getAllAuthors() {
        return libraryMapper.toAuthorDtos(authorDao.findAll());
    }

    @Transactional(readOnly = true)
    public Optional<AuthorDto> getAuthorById(long id) {
        Author author = authorDao.findById(id);

        if (author == null) {
            throw new AuthorDoesNotExistException(id);
        }

        return Optional.of(libraryMapper.toAuthorDto(author));
    }

    @Transactional
    public void createAuthor(String firstName, String lastName) {
        Author author = new Author(firstName, lastName);
        authorDao.create(author);
    }

    @Transactional
    public void updateAuthor(long id, String firstName, String lastName) {
        Author author = authorDao.findById(id);
        if (author == null) {
            throw new AuthorDoesNotExistException(id);
        }

        author.setFirstName(firstName);
        author.setLastName(lastName);

        authorDao.update(author);
    }

    @Transactional
    public void deleteAuthor(long id) {
        if (authorDao.findById(id) == null) {
            throw new AuthorDoesNotExistException(id);
        }
        authorDao.delete(id);
    }
}
