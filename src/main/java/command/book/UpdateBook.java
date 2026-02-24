package command.book;

import command.Command;
import dto.AuthorDto;
import dto.BookDto;
import dto.GenreDto;
import exception.*;
import i18n.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import service.AuthorService;
import service.BookService;
import service.GenreService;

import java.util.*;

@Component
@RequiredArgsConstructor
public class UpdateBook implements Command {
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

        if (!isDbValid(books, authors, genres)) return;

        while (true) {
            books.forEach(System.out::println);

            long id = getChoiceId();
            if (id == -1) break;

            String title = getString("book.update.title", "book.update.title.invalid");
            if (title.isEmpty()) continue;

            String description = getString("book.update.description",  "book.update.description.invalid");
            if (description.isEmpty()) continue;

            Optional<Set<Long>> authorIds = assignAuthors(authors);
            if (authorIds.isEmpty()) continue;

            Optional<Set<Long>> genreIds = assignGenres(genres);
            if (genreIds.isEmpty()) continue;

            try{
                bookService.updateBook(id, title, description, authorIds.get(), genreIds.get());
            } catch(AuthorDoesNotExistException | GenreDoesNotExistException e) {
                System.out.println(e.getMessage());
            }
            break;
        }
    }

    private Optional<Set<Long>> assignAuthors(List<AuthorDto> authors){
        authors.forEach(System.out::println);
        System.out.println(messages.get("book.update.author"));
        String author = scanner.nextLine().trim();

        return messages.parseLongSetOrPrint(author);
    }

    private Optional<Set<Long>> assignGenres(List<GenreDto> genres){
        genres.forEach(System.out::println);
        System.out.println(messages.get("book.update.genre"));
        String genre = scanner.nextLine().trim();

        return messages.parseLongSetOrPrint(genre);
    }

    private String getString(String request, String exceptionKey){
        System.out.println(messages.get(request));

        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println(messages.get(exceptionKey));
        }

        return name;
    }

    private long getChoiceId(){
        System.out.println(messages.get("book.update.id"));
        OptionalLong id = messages.parseLongOrPrint(scanner.nextLine().trim());

        try {
            bookService.getBookById(id);
        } catch (InvalidIdFormatException | BookDoesNotExistException e) {
            System.out.println(e.getMessage());
            return -1;
        }

        return id.getAsLong();
    }

    private boolean isDbValid(List<BookDto> books, List<AuthorDto> authors, List<GenreDto> genres){
        if (books.isEmpty()){
            System.out.println(messages.get("book.update.noBooks"));
            return false;
        } else if (authors.isEmpty()){
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
