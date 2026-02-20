package command.author;

import i18n.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import service.AuthorService;

@Component
@RequiredArgsConstructor
public class DisplayAllAuthors implements AuthorCommand {

    private final AuthorService authorService;
    private final Messages messages;

    @Override
    public void execute() {
        var authors = authorService.getAllAuthors();

        if (authors.isEmpty()) {
            System.out.println(messages.get("author.empty"));
        } else {
            authors.forEach(System.out::println);
        }
    }
}