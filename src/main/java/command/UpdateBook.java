package command;

import exception.BookDoesNotExistException;
import i18n.Messages;
import org.springframework.stereotype.Component;
import service.BookService;

import java.util.OptionalInt;
import java.util.Scanner;

@Component
public class UpdateBook implements Command {
    private final Scanner scanner;
    private final BookService bookService;
    private final Messages messages;
    private final CommandExecutor commandExecutor;

    public UpdateBook(Scanner scanner, BookService bookService, Messages messages, CommandExecutor commandExecutor) {
        this.scanner = scanner;
        this.bookService = bookService;
        this.messages = messages;
        this.commandExecutor = commandExecutor;
    }

    @Override
    public void execute() {
        bookService.readAllBooks().forEach(System.out::println);

        System.out.println(messages.get("update.id"));

        String inputId = scanner.nextLine().trim();
        OptionalInt id = messages.parseIntOrPrint(inputId);
        if (id.isEmpty()) return;

        System.out.println(messages.get("update.options"));
        System.out.println(messages.get("update.options.title"));
        System.out.println(messages.get("update.options.author"));
        System.out.println(messages.get("update.options.description"));
        System.out.println(messages.get("update.options.everything"));

        String inputOption = scanner.nextLine().trim();
        OptionalInt option = messages.parseIntOrPrint(inputOption);
        if (option.isEmpty() || !doesBookExist(option.getAsInt())) return;

        switch (option.getAsInt()) {
            case 1 -> {
                System.out.println(messages.get("update.title"));
                String newTitle = scanner.nextLine().trim();

                commandExecutor.executeAction(() -> bookService.updateBookTitle(id.getAsInt(), newTitle), messages.get("update.successful.title"));
            }
            case 2 -> {
                System.out.println(messages.get("update.author"));
                String newAuthor = scanner.nextLine().trim();

                commandExecutor.executeAction(() -> bookService.updateBookAuthor(id.getAsInt(), newAuthor), messages.get("update.successful.author"));
            }
            case 3 -> {
                System.out.println(messages.get("update.description"));
                String newDescription = scanner.nextLine().trim();

                commandExecutor.executeAction(() -> bookService.updateBookDescription(id.getAsInt(), newDescription), messages.get("update.successful.description"));
            }
            case 4 -> {
                System.out.println(messages.get("update.title"));
                String newTitle = scanner.nextLine().trim();
                System.out.println(messages.get("update.author"));
                String newAuthor = scanner.nextLine().trim();
                System.out.println(messages.get("update.description"));
                String newDescription = scanner.nextLine().trim();

                commandExecutor.executeAction(() -> bookService.updateBook(id.getAsInt(), newTitle, newAuthor, newDescription), messages.get("update.successful.everything"));
            }
            default -> System.out.println(messages.get("update.invalid.option"));
        }
    }

    @Override
    public int id() {
        return 3;
    }

    private boolean doesBookExist(int id) {
        try {
            bookService.getBookById(id);
            return true;
        } catch (BookDoesNotExistException e) {
            System.out.println(messages.get("book.exception"));
            return false;
        }
    }
}