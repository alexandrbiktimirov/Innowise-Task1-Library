package command.author;

import exception.AuthorDoesNotExistException;
import i18n.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import service.AuthorService;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class DeleteAuthor implements AuthorCommand {
    private final AuthorService authorService;
    private final Messages messages;
    private final Scanner scanner;

    @Override
    public void execute() {
        authorService.getAllAuthors().forEach(System.out::println);
        System.out.println(messages.get("author.delete.select"));

        String choice = scanner.nextLine().trim();

        try {
            long choiceInt = Long.parseLong(choice);
            authorService.deleteAuthor(choiceInt);
            System.out.println(messages.get("author.delete.successful"));
        } catch(AuthorDoesNotExistException e) {
            System.out.println(messages.get("author.notfound"));
        }
    }
}
