package command.author;

import command.Command;
import exception.AuthorDoesNotExistException;
import i18n.Messages;
import org.springframework.stereotype.Component;
import service.AuthorService;

import java.util.OptionalLong;
import java.util.Scanner;

@Component
public class UpdateAuthor implements Command {

    private final AuthorService authorService;
    private final Scanner scanner;
    private final Messages messages;

    public UpdateAuthor(AuthorService authorService, Scanner scanner, Messages messages) {
        this.authorService = authorService;
        this.scanner = scanner;
        this.messages = messages;
    }

    @Override
    public void execute() {
        if (authorService.getAllAuthors().isEmpty()) {
            System.out.println(messages.get("author.empty"));
            return;
        }

        while (true) {
            authorService.getAllAuthors().forEach(System.out::println);

            System.out.println(messages.get("author.update.id"));

            OptionalLong id = messages.parseLongOrPrint(scanner.nextLine().trim());
            if (id.isEmpty()) {
                System.out.println(messages.get("author.notfound"));
                break;
            }

            try {
                authorService.getAuthorById(id.getAsLong());
            } catch (AuthorDoesNotExistException e) {
                System.out.println(messages.get("author.notfound"));
                break;
            }

            System.out.println(messages.get("author.update.firstName"));
            String firstName = scanner.nextLine().trim();

            if (firstName.isEmpty()) {
                System.out.println(messages.get("author.firstName.empty"));
                continue;
            }

            System.out.println(messages.get("author.update.lastName"));
            String lastName = scanner.nextLine().trim();

            if (lastName.isEmpty()) {
                System.out.println(messages.get("author.lastName.empty"));
                continue;
            }

            try {
                authorService.updateAuthor(id.getAsLong(), firstName, lastName);
            } catch (AuthorDoesNotExistException e) {
                System.out.println(messages.get("author.notfound"));
                continue;
            }
            break;
        }
    }
}
