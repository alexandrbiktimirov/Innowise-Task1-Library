package command.book;

import command.Command;
import dto.BookDto;
import i18n.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import service.BookService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DisplayAllBooks implements Command {
    private final BookService bookService;
    private final Messages messages;

    @Override
    public void execute() {
        List<BookDto> books = bookService.getAllBooks();

        if (books.isEmpty()) {
            System.out.println(messages.get("book.empty"));
        } else{
            books.forEach(System.out::println);
        }
    }
}
