package command.book;

import command.Command;
import exception.BookDoesNotExistException;
import exception.InvalidIdFormatException;
import i18n.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import service.BookService;

import java.util.OptionalLong;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class DeleteBook implements Command {
    private final Scanner scanner;
    private final BookService bookService;
    private final Messages messages;

    @Override
    public void execute() {
        try {
            bookService.getAllBooks().forEach(System.out::println);
            System.out.println(messages.get("book.delete.id"));

            String inputId = scanner.nextLine().trim();
            OptionalLong id = messages.parseLongOrPrint(inputId);

            bookService.deleteBook(id.getAsLong());
            System.out.println(messages.get("book.delete.successful"));
        } catch (BookDoesNotExistException | InvalidIdFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}
