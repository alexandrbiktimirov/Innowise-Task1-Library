package command.book;

import command.Command;
import exception.AuthorDoesNotExistException;
import exception.GenreDoesNotExistException;
import i18n.Messages;
import org.springframework.stereotype.Component;
import service.AuthorService;
import service.BookService;
import service.GenreService;

import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

@Component
public class CreateNewBook implements Command {
    private final Scanner scanner;
    private final Messages messages;
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    public CreateNewBook(Scanner scanner, Messages messages, BookService bookService, AuthorService authorService, GenreService genreService) {
        this.scanner = scanner;
        this.messages = messages;
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public void execute() {
        while (true) {
            System.out.println(messages.get("book.create.title"));
            String title = scanner.nextLine().trim();

            if (title.isEmpty()){
                System.out.println(messages.get("book.create.title.invalid"));
                continue;
            }

            System.out.println(messages.get("book.create.description"));
            String description = scanner.nextLine().trim();

            if (description.isEmpty()){
                System.out.println(messages.get("book.create.description.invalid"));
                continue;
            }

            System.out.println(messages.get("book.create.author"));
            String author = scanner.nextLine().trim();
            Optional<Set<Long>> authorIds = messages.parseLongSetOrPrint(author);

            if (authorIds.isEmpty() || !authorsExist(authorIds.get())){
                System.out.println(messages.get("author.notfound"));
                break;
            }

            System.out.println(messages.get("book.create.genre"));
            String genre = scanner.nextLine().trim();
            Optional<Set<Long>> genreIds = messages.parseLongSetOrPrint(genre);

            if (genreIds.isEmpty() || !genresExist(genreIds.get())){
                System.out.println(messages.get("genre.notfound"));
                break;
            }

            try {
                bookService.createBook(title, description, authorIds.get(), genreIds.get());
            } catch (AuthorDoesNotExistException e) {
                System.out.println(messages.get("author.notfound"));
                continue;
            } catch (GenreDoesNotExistException e) {
                System.out.println(messages.get("genre.notfound"));
                continue;
            }
            break;
        }
    }

    private boolean authorsExist(Set<Long> authorIds) {
        try {
            authorIds.forEach(authorService::getAuthorById);
            return true;
        } catch (AuthorDoesNotExistException e) {
            return false;
        }
    }

    private boolean genresExist(Set<Long> genreIds) {
        try {
            genreIds.forEach(genreService::getGenreById);
            return true;
        } catch (GenreDoesNotExistException e) {
            return false;
        }
    }
}
