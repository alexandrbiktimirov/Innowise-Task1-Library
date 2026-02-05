package command.genre;

import command.Command;
import i18n.Messages;
import org.springframework.stereotype.Component;
import service.GenreService;

import java.util.OptionalLong;
import java.util.Scanner;

@Component
public class UpdateGenre implements Command {
    private final Scanner scanner;
    private final Messages messages;
    private final GenreService genreService;

    public UpdateGenre(Scanner scanner, Messages messages, GenreService genreService) {
        this.scanner = scanner;
        this.messages = messages;
        this.genreService = genreService;
    }

    @Override
    public void execute() {
        if (genreService.getAllGenres().isEmpty()) {
            System.out.println(messages.get("genre.empty"));
            return;
        }

        while (true) {
            genreService.getAllGenres().forEach(System.out::println);

            System.out.println(messages.get("genre.update.id"));

            OptionalLong id = messages.parseLongOrPrint(scanner.nextLine().trim());
            if (id.isEmpty() || genreService.getGenreById(id.getAsLong()) == null) {
                System.out.println(messages.get("genre.notfound"));
                continue;
            }

            System.out.println(messages.get("genre.update.name"));
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println(messages.get("genre.name.empty"));
                continue;
            }

            genreService.updateGenre(id.getAsLong(), name);
            break;
        }
    }
}