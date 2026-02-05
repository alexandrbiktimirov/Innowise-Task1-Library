package command.book;

import command.Command;
import i18n.Messages;
import org.springframework.stereotype.Component;
import service.AuthorService;
import service.BookService;
import service.GenreService;

import java.util.OptionalLong;
import java.util.Scanner;

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
            OptionalLong authorId = messages.parseLongOrPrint(author);

            if (authorId.isEmpty() || authorService.getAuthorById(authorId.getAsLong()) == null){
                System.out.println(messages.get("author.notfound"));
                break;
            }

            System.out.println(messages.get("book.create.genre"));
            String genre = scanner.nextLine().trim();
            OptionalLong genreId = messages.parseLongOrPrint(genre);

            if (genreId.isEmpty() || genreService.getGenreById(genreId.getAsLong()) == null){
                System.out.println(messages.get("genre.notfound"));
                break;
            }

            bookService.createBook(title, description, authorId.getAsLong(), genreId.getAsLong());
            break;
        }
    }
}