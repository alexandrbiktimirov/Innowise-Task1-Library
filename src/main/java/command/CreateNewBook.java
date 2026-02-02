package command;

import i18n.Messages;
import org.springframework.stereotype.Component;
import service.BookService;

import java.util.Scanner;

@Component
public class CreateNewBook implements Command {
    private final Scanner scanner;
    private final BookService bookService;
    private final Messages messages;

    public CreateNewBook(Scanner scanner, BookService bookService, Messages messages) {
        this.scanner = scanner;
        this.bookService = bookService;
        this.messages = messages;
    }

    @Override
    public void execute() {
        while (true) {
            System.out.println(messages.get("create.name"));
            String name = scanner.nextLine().trim();

            if (isEmpty(name, messages.get("create.invalid.name"))) continue;

            System.out.println(messages.get("create.author"));
            String author = scanner.nextLine().trim();

            if (isEmpty(author, messages.get("create.invalid.author"))) continue;

            System.out.println(messages.get("create.description"));
            String description = scanner.nextLine().trim();

            if (isEmpty(description, messages.get("create.invalid.description"))) continue;

            bookService.createBook(name, author, description);
            break;
        }
    }

    @Override
    public int id() {
        return 2;
    }

    private boolean isEmpty(String something, String message) {
        if (something.isEmpty()) {
            System.out.println(message);
            return true;
        }
        return false;
    }
}