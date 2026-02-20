package command.book;

import i18n.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import service.BookService;

@Component
@RequiredArgsConstructor
public class DisplayAllBooks implements BookCommand {
    private final BookService bookService;
    private final Messages messages;

    @Override
    public void execute() {
        var books = bookService.getAllBooks();

        if (books.isEmpty()) {
            System.out.println(messages.get("book.empty"));
        }

        books.forEach(System.out::println);
    }
}
