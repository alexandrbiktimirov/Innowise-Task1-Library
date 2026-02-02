package command;

import org.springframework.stereotype.Component;
import service.BookService;


@Component
public class DisplayAllBooks implements Command {
    private final BookService bookService;

    public DisplayAllBooks(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute() {
        bookService.readAllBooks().forEach(System.out::println);
    }

    @Override
    public int id() {
        return 1;
    }
}