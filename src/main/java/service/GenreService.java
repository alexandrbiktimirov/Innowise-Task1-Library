package service;

import dto.GenreDto;
import exception.GenreDoesNotExistException;
import mapper.LibraryMapper;
import model.Genre;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.GenreDao;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    private final GenreDao genreDao;
    private final LibraryMapper libraryMapper;

    public GenreService(GenreDao genreDao, LibraryMapper libraryMapper) {
        this.genreDao = genreDao;
        this.libraryMapper = libraryMapper;
    }

    @Transactional(readOnly = true)
    public List<GenreDto> getAllGenres() {
        return libraryMapper.toGenreDtos(genreDao.findAll());
    }

    @Transactional(readOnly = true)
    public Optional<GenreDto> getGenreById(Long id) {
        Genre genre = genreDao.findById(id);

        if (genre == null) {
            throw new GenreDoesNotExistException(id);
        }

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
            throw new GenreDoesNotExistException(id);
        }

        genre.setName(name);

        genreDao.update(genre);
    }

    @Transactional
    public void deleteGenre(Long id) {
        if (genreDao.findById(id) == null) {
            throw new GenreDoesNotExistException(id);
        }
        genreDao.delete(id);
    }
}
