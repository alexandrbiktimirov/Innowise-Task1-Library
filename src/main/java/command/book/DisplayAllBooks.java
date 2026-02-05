package command.book;

import command.Command;
import i18n.Messages;
import org.springframework.stereotype.Component;
import service.BookService;

@Component
public class DisplayAllBooks implements Command {
    private final BookService bookService;
    private final Messages messages;

    public DisplayAllBooks(BookService bookService,  Messages messages) {
        this.bookService = bookService;
        this.messages = messages;
    }

    @Override
    public void execute() {
        if (bookService.getAllBooks().isEmpty()) {
            System.out.println(messages.get("start.empty.option"));
        }

        bookService.getAllBooks().forEach(System.out::println);
    }
}