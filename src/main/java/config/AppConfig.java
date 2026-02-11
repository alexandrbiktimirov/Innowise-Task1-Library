package config;

import command.Command;
import command.QuitProgram;
import controller.AppController;
import controller.AuthorController;
import controller.BookController;
import controller.GenreController;
import i18n.LocaleHolder;
import i18n.Messages;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import repository.AuthorDao;
import repository.BookDao;
import repository.GenreDao;
import service.AuthorService;
import service.BookService;
import service.GenreService;

import java.util.Map;
import java.util.Scanner;

@Configuration
@Import({AopConfig.class, CommandsConfig.class, I18nConfig.class, HibernateConfig.class})
public class AppConfig {

    @Bean
    public BookService bookService(BookDao bookDao) {
        return new BookService(bookDao);
    }

    @Bean
    public AuthorService authorService(AuthorDao authorDao) {
        return new AuthorService(authorDao);
    }

    @Bean
    public GenreService genreService(GenreDao genreDao) {
        return new GenreService(genreDao);
    }

    @Bean
    public AppController appController(AuthorController authorController, BookController bookController, GenreController genreController, LocaleHolder localeHolder, Messages messages, Scanner scanner, QuitProgram quitProgram) {
        return new AppController(authorController, bookController, genreController, localeHolder, messages, scanner, quitProgram);
    }

    @Bean
    public AuthorController authorController(Scanner scanner, Messages messages, @Qualifier("authorCommands") Map<Integer, Command> authorCommands) {
        return new AuthorController(scanner, messages, authorCommands);
    }

    @Bean
    public BookController bookController(Scanner scanner, Messages messages, @Qualifier("bookCommands") Map<Integer, Command> bookCommands) {
        return new BookController(scanner, messages, bookCommands);
    }

    @Bean
    public GenreController genreController(Scanner scanner, Messages messages, @Qualifier("genreCommands") Map<Integer, Command> genreCommands) {
        return new GenreController(scanner, messages, genreCommands);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}