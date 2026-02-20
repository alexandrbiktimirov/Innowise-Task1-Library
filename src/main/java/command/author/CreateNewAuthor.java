package command.author;

import i18n.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import service.AuthorService;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class CreateNewAuthor implements AuthorCommand {
    private final AuthorService authorService;
    private final Messages messages;
    private final Scanner scanner;

    @Override
    public void execute() {
        while (true) {
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

            authorService.createAuthor(firstName, lastName);
            break;
        }
    }
}
