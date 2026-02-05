package command.author;

import command.Command;
import i18n.Messages;
import org.springframework.stereotype.Component;
import service.AuthorService;

@Component
public class DisplayAllAuthors implements Command {

    private final AuthorService authorService;
    private final Messages messages;

    public DisplayAllAuthors(AuthorService authorService, Messages messages) {
        this.authorService = authorService;
        this.messages = messages;
    }

    @Override
    public void execute() {
        if (authorService.getAllAuthors().isEmpty()) {
            System.out.println(messages.get("author.empty"));
        } else{
            authorService.getAllAuthors().forEach(System.out::println);
        }
    }
}
