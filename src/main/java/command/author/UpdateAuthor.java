package command.author;

import command.Command;
import org.springframework.stereotype.Component;
import service.AuthorService;

@Component
public class UpdateAuthor implements Command {
    private AuthorService authorService;

    public UpdateAuthor(AuthorService authorService) {

    }

    @Override
    public void execute() {

    }
}
