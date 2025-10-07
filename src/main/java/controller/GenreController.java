package controller;

import org.springframework.stereotype.Controller;
import service.GenreService;

@Controller
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }
}
