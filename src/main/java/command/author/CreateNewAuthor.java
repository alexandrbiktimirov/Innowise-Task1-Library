package command.author;

import command.Command;
import i18n.Messages;
import org.springframework.stereotype.Component;
import service.AuthorService;

import java.util.Scanner;

@Component
public class CreateNewAuthor implements Command {
    private final AuthorService authorService;
    private final Messages messages;
    private final Scanner scanner;

    public CreateNewAuthor(AuthorService authorService, Messages messages, Scanner scanner) {
        this.authorService = authorService;
        this.messages = messages;
        this.scanner = scanner;
    }

    @Override
    public void execute() {

    }
}
