package command.book;

import command.Command;
import dto.AuthorDto;
import dto.BookDto;
import dto.GenreDto;
import exception.AuthorDoesNotExistException;
import exception.GenreDoesNotExistException;
import i18n.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import service.AuthorService;
import service.BookService;
import service.GenreService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CreateNewBook implements Command {
    private final Scanner scanner;
    private final Messages messages;
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public void execute() {
        var authors = authorService.getAllAuthors();
        var genres = genreService.getAllGenres();

        if (!isDbValid(authors, genres)) return;

        while (true) {
            String title = getString("book.create.title", "book.create.title.invalid");
            if (title == null) continue;

            String description = getString("book.create.description", "book.create.description.invalid");
            if (description == null) continue;

            System.out.println(messages.get("book.create.author"));
            String authorsInput = scanner.nextLine().trim();
            Optional<Set<Long>> authorIds = messages.parseLongSetOrPrint(authorsInput);
            if (authorIds.isEmpty()) continue;

            System.out.println(messages.get("book.create.genre"));
            String genresInput = scanner.nextLine().trim();
            Optional<Set<Long>> genreIds = messages.parseLongSetOrPrint(genresInput);
            if (genreIds.isEmpty()) continue;

            try{
                bookService.createBook(title, description, authorIds.get(), genreIds.get());
            } catch(AuthorDoesNotExistException | GenreDoesNotExistException e){
                System.out.println(e.getMessage());
            }
            break;
        }
    }

    private String getString(String key, String exceptionKey) {
        System.out.println(messages.get(key));

        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println(messages.get(exceptionKey));
            return null;
        }

        return input;
    }

    private boolean isDbValid(List<AuthorDto> authors, List<GenreDto> genres){
        if (authors.isEmpty()){
            System.out.println(messages.get("book.update.noAuthors"));
            return false;
        } else if (genres.isEmpty()){
            System.out.println(messages.get("book.update.noGenres"));
            return false;
        } else{
            return true;
        }
    }
}
