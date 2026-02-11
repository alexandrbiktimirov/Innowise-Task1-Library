package command.author;

import command.Command;
import exception.AuthorDoesNotExistException;
import i18n.Messages;
import org.springframework.stereotype.Component;
import service.AuthorService;

import java.util.Scanner;

@Component
public class DeleteAuthor implements Command {
    private final AuthorService authorService;
    private final Messages messages;
    private final Scanner scanner;

    public DeleteAuthor(AuthorService authorService, Messages messages, Scanner scanner) {
        this.authorService = authorService;
        this.messages = messages;
        this.scanner = scanner;
    }

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
        } catch(Exception e) {
            System.out.println(messages.get("author.delete.invalid"));
        }
    }
}
