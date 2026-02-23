package service;

import dto.GenreDto;
import exception.GenreDoesNotExistException;
import exception.InvalidIdFormatException;
import i18n.Messages;
import lombok.RequiredArgsConstructor;
import mapper.LibraryMapper;
import model.Genre;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.GenreDao;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreDao genreDao;
    private final Messages messages;
    private final LibraryMapper libraryMapper;

    @Transactional(readOnly = true)
    public List<GenreDto> getAllGenres() {
        return libraryMapper.toGenreDtos(genreDao.findAll());
    }

    @Transactional(readOnly = true)
    public Optional<GenreDto> getGenreById(OptionalLong genreId) {
        if (genreId.isEmpty()) throw new InvalidIdFormatException(messages.get("common.invalid.format"));

        var id = genreId.getAsLong();
        Genre genre = genreDao.findById(id);

        if (genre == null) throw new GenreDoesNotExistException(messages.get("genre.notfound", id));

        return Optional.of(libraryMapper.toGenreDto(genre));
    }

    @Transactional
    public void createGenre(String name) {
        Genre genre = new Genre(name);

        genreDao.create(genre);
    }

    @Transactional
    public void updateGenre(long id, String name) {
        Genre genre = genreDao.findById(id);
        if (genre == null) {
            throw new GenreDoesNotExistException(messages.get("genre.notfound", id));
        }

        genre.setName(name);

        genreDao.update(genre);
    }

    @Transactional
    public void deleteGenre(Long id) {
        if (genreDao.findById(id) == null) {
            throw new GenreDoesNotExistException(messages.get("genre.notfound", id));
        }
        genreDao.delete(id);
    }
}
