package service;

import model.Genre;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface GenreService {

    List<Genre> getAllGenres();

    Genre getGenreById(Long id);

    @Transactional
    void createGenre(Genre genre);

    @Transactional
    void updateGenre(Genre genre);

    @Transactional
    void deleteGenre(Long id);
}