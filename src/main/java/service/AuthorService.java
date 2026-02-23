package service;

import dto.AuthorDto;
import exception.AuthorDoesNotExistException;
import exception.InvalidIdFormatException;
import i18n.Messages;
import lombok.RequiredArgsConstructor;
import mapper.LibraryMapper;
import model.Author;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.AuthorDao;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorDao authorDao;
    private final Messages messages;
    private final LibraryMapper libraryMapper;

    @Transactional(readOnly = true)
    public List<AuthorDto> getAllAuthors() {
        return libraryMapper.toAuthorDtos(authorDao.findAll());
    }

    @Transactional(readOnly = true)
    public Optional<AuthorDto> getAuthorById(OptionalLong optionalId) {
        if (optionalId.isEmpty()){
            throw new InvalidIdFormatException(messages.get("common.invalid.format"));
        }

        var id = optionalId.getAsLong();

        Author author = authorDao.findById(id);

        if (author == null) {
            throw new AuthorDoesNotExistException(messages.get("author.notfound", id));
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
            throw new AuthorDoesNotExistException(messages.get("author.notfound", id));
        }

        author.setFirstName(firstName);
        author.setLastName(lastName);

        authorDao.update(author);
    }

    @Transactional
    public void deleteAuthor(long id) {
        if (authorDao.findById(id) == null) {
            throw new AuthorDoesNotExistException(messages.get("author.notfound", id));
        }
        authorDao.delete(id);
    }
}
