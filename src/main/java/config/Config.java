package config;

import controller.AuthorController;
import controller.BookController;
import controller.GenreController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import repository.AuthorDao;
import repository.BookDao;
import repository.GenreDao;
import service.*;

@Configuration
@Import({JdbcConfig.class, AopConfig.class})
public class Config {

    @Bean
    public AuthorService authorService(AuthorDao authorDao) {
        return new AuthorServiceImpl(authorDao);
    }

    @Bean
    public GenreService genreService(GenreDao genreDao) {
        return new GenreServiceImpl(genreDao);
    }

    @Bean
    public BookServiceImpl bookService(BookDao bookDao) {
        return new BookServiceImpl(bookDao);
    }

    @Bean
    public AuthorController authorController(AuthorService authorService){
        return new AuthorController(authorService);
    }

    @Bean
    public GenreController genreController(GenreService genreService){
        return new GenreController(genreService);
    }

    @Bean
    public BookController bookController(BookService bookService){
        return new BookController(bookService);
    }
}
