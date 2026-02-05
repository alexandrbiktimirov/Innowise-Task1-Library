package service;

import aop.Cached;
import model.Genre;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.GenreDao;

import java.util.List;

@Service
public class GenreService {

    private final GenreDao genreDao;

    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public List<Genre> getAllGenres() {
        return genreDao.findAll();
    }

    @Cached
    public Genre getGenreById(Long id) {
        try {
            return genreDao.findById(id);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Transactional
    public void createGenre(String name) {
        Genre genre = new Genre(name);

        genreDao.create(genre);
    }

    @Transactional
    public void updateGenre(long id, String name) {
        Genre genre = getGenreById(id);
        genre.setName(name);

        genreDao.update(genre);
    }

    @Transactional
    public void deleteGenre(Long id) {
        genreDao.delete(id);
    }
}