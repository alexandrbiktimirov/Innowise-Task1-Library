package service;

import aop.Cached;
import model.Genre;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.GenreDao;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreDao.findAll();
    }

    @Override
    @Cached
    public Genre getGenreById(Long id) {
        try {
            return genreDao.findById(id);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Transactional
    @Override
    public void createGenre(Genre genre) {
        genreDao.create(genre);
    }

    @Transactional
    @Override
    public void updateGenre(Genre genre) {
        genreDao.update(genre);
    }

    @Transactional
    @Override
    public void deleteGenre(Long id) {
        genreDao.delete(id);
    }
}