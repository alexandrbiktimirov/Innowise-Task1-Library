package command.author;

import command.Command;
import exception.AuthorDoesNotExistException;
import exception.InvalidIdFormatException;
import i18n.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import service.AuthorService;

import java.util.OptionalLong;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class UpdateAuthor implements Command {

    private final AuthorService authorService;
    private final Scanner scanner;
    private final Messages messages;

    @Override
    public void execute() {
        var authorsList = authorService.getAllAuthors();

        if (authorsList.isEmpty()) {
            System.out.println(messages.get("author.empty"));
            return;
        }

        while (true) {
            authorsList.forEach(System.out::println);

            var id = getChoiceId();
            if (id == -1) break;

            String firstName = getName("author.update.firstName", "author.firstName.empty");
            if (firstName.isEmpty()) continue;

            String lastName = getName("author.update.lastName", "author.lastName.empty");
            if (lastName.isEmpty()) continue;

            authorService.updateAuthor(id, firstName, lastName);
            break;
        }
    }

    private String getName(String request, String exceptionKey){
        System.out.println(messages.get(request));

        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println(messages.get(exceptionKey));
        }

        return name;
    }

    private long getChoiceId(){
        System.out.println(messages.get("author.update.id"));
        OptionalLong id = messages.parseLongOrPrint(scanner.nextLine().trim());

        try {
            authorService.getAuthorById(id);
        } catch (InvalidIdFormatException | AuthorDoesNotExistException e) {
            System.out.println(e.getMessage());
            return -1;
        }

        return id.getAsLong();
    }
}
