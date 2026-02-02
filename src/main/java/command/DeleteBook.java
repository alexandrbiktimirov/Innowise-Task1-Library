package command;

import i18n.Messages;
import org.springframework.stereotype.Component;
import service.BookService;

import java.util.OptionalInt;
import java.util.Scanner;

@Component
public class DeleteBook implements Command {
    private final Scanner scanner;
    private final BookService bookService;
    private final Messages messages;
    private final CommandExecutor commandExecutor;

    public DeleteBook(Scanner scanner, BookService bookService, Messages messages, CommandExecutor commandExecutor) {
        this.scanner = scanner;
        this.bookService = bookService;
        this.messages = messages;
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void execute() {
        bookService.readAllBooks().forEach(System.out::println);
        System.out.println(messages.get("delete.id"));

        String inputId = scanner.nextLine().trim();
        OptionalInt id = messages.parseIntOrPrint(inputId);

        if (id.isEmpty()) return;

        commandExecutor.executeAction(() -> bookService.deleteBook(id.getAsInt()), messages.get("delete.successful"));
    }

    @Override
    public int id() {
        return 4;
    }
}