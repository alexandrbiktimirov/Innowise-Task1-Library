package service;

import model.Genre;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface GenreService {

    List<Genre> getAllGenres();

    Genre getGenreById(Long id);

    @Transactional
    void createGenre(String name);

    @Transactional
    void updateGenre(long id, String name);

    @Transactional
    void deleteGenre(Long id);
}