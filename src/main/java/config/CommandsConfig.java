package config;

import command.Command;
import command.ReturnToMainMenu;
import command.author.*;
import command.book.*;
import command.genre.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Scanner;

@Configuration
public class CommandsConfig {

    @Bean
    public Scanner scanner(){
        return new Scanner(System.in);
    }

    @Bean("bookCommands")
    public Map<Integer, Command> bookCommands(DisplayAllBooks displayAllBooks, CreateNewBook createNewBook, UpdateBook updateBook, DeleteBook deleteBook, ReturnToMainMenu returnToMainMenu){
        return Map.of(
                1, displayAllBooks,
                2, createNewBook,
                3, updateBook,
                4, deleteBook,
                5, returnToMainMenu
        );
    }

    @Bean("authorCommands")
    public Map<Integer, Command> authorCommands(DisplayAllAuthors displayAllAuthors, CreateNewAuthor createNewAuthor, UpdateAuthor updateAuthor, DeleteAuthor deleteAuthor, ReturnToMainMenu returnToMainMenu){
        return Map.of(
                1, displayAllAuthors,
                2, createNewAuthor,
                3, updateAuthor,
                4, deleteAuthor,
                5, returnToMainMenu
        );
    }

    @Bean("genreCommands")
    public Map<Integer, Command> genreCommands(DisplayAllGenres displayAllGenres, CreateNewGenre createNewGenre, UpdateGenre updateGenre, DeleteGenre deleteGenre, ReturnToMainMenu returnToMainMenu){
        return Map.of(
                1, displayAllGenres,
                2, createNewGenre,
                3, updateGenre,
                4, deleteGenre,
                5, returnToMainMenu
        );
    }
}
