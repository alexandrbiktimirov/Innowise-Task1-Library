package command.book;

import i18n.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import service.AuthorService;
import service.BookService;
import service.GenreService;

import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CreateNewBook implements BookCommand {
    private final Scanner scanner;
    private final Messages messages;
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public void execute() {
        while (true) {
            String title = getString("book.create.title", "book.create.title.invalid");
            if (title == null) continue;

            String description = getString("book.create.description", "book.create.description.invalid");
            if (description == null) continue;

            System.out.println(messages.get("book.create.author"));
            String authors = scanner.nextLine().trim();

            Optional<Set<Long>> authorIds = messages.parseLongSetOrPrint(authors);

            if (authorIds.isEmpty() || !authorsExist(authorIds.get())) {
                System.out.println(messages.get("author.notfound"));
                break;
            }

            System.out.println(messages.get("book.create.genre"));
            String genre = scanner.nextLine().trim();
            Optional<Set<Long>> genreIds = messages.parseLongSetOrPrint(genre);

            if (genreIds.isEmpty() || !genresExist(genreIds.get())) {
                System.out.println(messages.get("genre.notfound"));
                break;
            }

            bookService.createBook(title, description, authorIds.get(), genreIds.get());
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

    private boolean authorsExist(Set<Long> authorIds) {
        return authorIds.size() == authorService.countAuthors(authorIds);
    }

    private boolean genresExist(Set<Long> genreIds) {
        return genreIds.size() == genreService.countGenres(genreIds);
    }
}
