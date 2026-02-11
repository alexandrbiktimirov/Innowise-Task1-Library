package command.book;

import command.Command;
import exception.BookDoesNotExistException;
import i18n.Messages;
import org.springframework.stereotype.Component;
import service.BookService;

import java.util.OptionalLong;
import java.util.Scanner;

@Component
public class DeleteBook implements Command {
    private final Scanner scanner;
    private final BookService bookService;
    private final Messages messages;

    public DeleteBook(Scanner scanner, BookService bookService, Messages messages) {
        this.scanner = scanner;
        this.bookService = bookService;
        this.messages = messages;
    }

    @Override
    public void execute() {
        bookService.getAllBooks().forEach(System.out::println);
        System.out.println(messages.get("book.delete.id"));

        String inputId = scanner.nextLine().trim();
        OptionalLong id = messages.parseLongOrPrint(inputId);

        if (id.isEmpty()) {
            System.out.println(messages.get("book.notfound"));
            return;
        }

        try {
            bookService.deleteBook(id.getAsLong());
            System.out.println(messages.get("book.delete.successful"));
        } catch (BookDoesNotExistException e) {
            System.out.println(messages.get("book.notfound"));
        }
    }
}
