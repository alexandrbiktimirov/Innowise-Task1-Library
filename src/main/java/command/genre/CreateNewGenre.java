package command.genre;

import i18n.Messages;
import org.springframework.stereotype.Component;
import service.GenreService;

import java.util.Scanner;

@Component
public class CreateNewGenre implements GenreCommand {

    private final GenreService genreService;
    private final Scanner scanner;
    private final Messages messages;

    public CreateNewGenre(GenreService genreService, Scanner scanner, Messages messages) {
        this.genreService = genreService;
        this.scanner = scanner;
        this.messages = messages;
    }

    @Override
    public void execute() {
        while (true) {
            System.out.println(messages.get("genre.create.name"));
            String name = scanner.nextLine().trim();

            if (name.isEmpty()){
                System.out.println(messages.get("genre.create.name.invalid"));
                continue;
            }

            genreService.createGenre(name);
            break;
        }
    }
}