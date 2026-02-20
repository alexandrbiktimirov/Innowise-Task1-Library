package command.book;

import exception.BookDoesNotExistException;
import i18n.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import service.AuthorService;
import service.BookService;
import service.GenreService;

import java.util.Optional;
import java.util.OptionalLong;
import java.util.Scanner;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UpdateBook implements BookCommand {
    private final Scanner scanner;
    private final Messages messages;
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public void execute() {
        var books = bookService.getAllBooks();
        var authors = authorService.getAllAuthors();
        var genres = genreService.getAllGenres();

        while (true) {
            books.forEach(System.out::println);

            System.out.println(messages.get("book.update.id"));

            String inputId = scanner.nextLine().trim();
            OptionalLong id = messages.parseLongOrPrint(inputId);
            if (id.isEmpty()) {
                System.out.println(messages.get("book.notfound"));
                continue;
            }

            try {
                bookService.getBookById(id.getAsLong());
            } catch (BookDoesNotExistException e) {
                System.out.println(messages.get("book.notfound"));
                continue;
            }

            System.out.println(messages.get("book.update.title"));
            String title = scanner.nextLine().trim();

            if (title.isEmpty()) {
                System.out.println(messages.get("book.update.title.invalid"));
                continue;
            }

            System.out.println(messages.get("book.update.description"));
            String description = scanner.nextLine().trim();

            if (description.isEmpty()) {
                System.out.println(messages.get("book.update.description.invalid"));
                continue;
            }

            System.out.println(messages.get("book.update.author"));
            String author = scanner.nextLine().trim();
            Optional<Set<Long>> authorIds = messages.parseLongSetOrPrint(author);

            if (authorIds.isEmpty() || !authorsExist(authorIds.get())) {
                System.out.println(messages.get("author.notfound"));
                continue;
            }

            System.out.println(messages.get("book.update.genre"));
            String genre = scanner.nextLine().trim();
            Optional<Set<Long>> genreIds = messages.parseLongSetOrPrint(genre);

            if (genreIds.isEmpty() || !genresExist(genreIds.get())) {
                System.out.println(messages.get("genre.notfound"));
                continue;
            }

            bookService.updateBook(id.getAsLong(), title, description, authorIds.get(), genreIds.get());
            break;
        }
    }

    private boolean authorsExist(Set<Long> authorIds) {
        return authorIds.size() == authorService.countAuthors(authorIds);
    }

    private boolean genresExist(Set<Long> genreIds) {
        return genreIds.size() == genreService.countGenres(genreIds);
    }
}
