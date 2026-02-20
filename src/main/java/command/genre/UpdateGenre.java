package command.genre;

import exception.GenreDoesNotExistException;
import i18n.Messages;
import org.springframework.stereotype.Component;
import service.GenreService;

import java.util.OptionalLong;
import java.util.Scanner;

@Component
public class UpdateGenre implements GenreCommand {
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
            if (id.isEmpty()) {
                System.out.println(messages.get("genre.notfound"));
                continue;
            }

            try {
                genreService.getGenreById(id.getAsLong());
            } catch (GenreDoesNotExistException e) {
                System.out.println(messages.get("genre.notfound"));
                continue;
            }

            System.out.println(messages.get("genre.update.name"));
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println(messages.get("genre.name.empty"));
                continue;
            }

            try {
                genreService.updateGenre(id.getAsLong(), name);
            } catch (GenreDoesNotExistException e) {
                System.out.println(messages.get("genre.notfound"));
                continue;
            }
            break;
        }
    }
}
