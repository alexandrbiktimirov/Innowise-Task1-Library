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
public class UpdateBook implements Command {
    private final Scanner scanner;
    private final Messages messages;
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    public UpdateBook(Scanner scanner, BookService bookService, Messages messages, AuthorService authorService, GenreService genreService) {
        this.scanner = scanner;
        this.messages = messages;
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public void execute() {
        while (true) {
            bookService.getAllBooks().forEach(System.out::println);

            System.out.println(messages.get("book.update.id"));

            String inputId = scanner.nextLine().trim();
            OptionalLong id = messages.parseLongOrPrint(inputId);
            if (id.isEmpty() || bookService.getBookById(id.getAsLong()) == null){
                System.out.println(messages.get("book.notfound"));
                continue;
            }

            System.out.println(messages.get("book.update.title"));
            String title = scanner.nextLine().trim();

            if (title.isEmpty()){
                System.out.println(messages.get("book.update.title.invalid"));
                continue;
            }

            System.out.println(messages.get("book.update.description"));
            String description = scanner.nextLine().trim();

            if (description.isEmpty()){
                System.out.println(messages.get("book.update.description.invalid"));
                continue;
            }

            System.out.println(messages.get("book.update.author"));
            String author = scanner.nextLine().trim();
            OptionalLong authorId = messages.parseLongOrPrint(author);

            if (authorId.isEmpty() || authorService.getAuthorById(authorId.getAsLong()) == null){
                System.out.println(messages.get("author.notfound"));
                continue;
            }

            System.out.println(messages.get("book.update.genre"));
            String genre = scanner.nextLine().trim();
            OptionalLong genreId = messages.parseLongOrPrint(genre);

            if (genreId.isEmpty() || genreService.getGenreById(genreId.getAsLong()) == null){
                System.out.println(messages.get("genre.notfound"));
                continue;
            }

            bookService.updateBook(id.getAsLong(), title, description, authorId.getAsLong(), genreId.getAsLong());
            break;
        }
    }
}