package command;

import i18n.Messages;
import org.springframework.stereotype.Component;
import service.BookService;

@Component
public class QuitProgram implements Command {
    private final BookService bookService;
    private final Messages messages;

    public QuitProgram(BookService bookService, Messages messages) {
        this.bookService = bookService;
        this.messages = messages;
    }

    @Override
    public void execute() {
        System.out.println(messages.get("quitting"));
        bookService.writeChangesToFile();
        System.exit(0);
    }

    @Override
    public int id() {
        return 5;
    }
}