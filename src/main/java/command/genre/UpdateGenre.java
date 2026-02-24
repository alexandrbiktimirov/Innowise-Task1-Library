package command.genre;

import command.Command;
import exception.BookDoesNotExistException;
import exception.InvalidIdFormatException;
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
        var genres = genreService.getAllGenres();

        if (genres.isEmpty()) {
            System.out.println(messages.get("genre.empty"));
            return;
        }

        while (true) {
            genres.forEach(System.out::println);

            long id = getChoiceId();
            if (id == -1) break;

            String name = getString("genre.update.name", "genre.name.empty");
            if (name.isEmpty()) continue;

            genreService.updateGenre(id, name);
            break;
        }
    }

    private String getString(String request, String exceptionKey){
        System.out.println(messages.get(request));

        String str = scanner.nextLine().trim();

        if (str.isEmpty()) {
            System.out.println(messages.get(exceptionKey));
        }

        return str;
    }

    private long getChoiceId(){
        System.out.println(messages.get("genre.update.id"));
        OptionalLong id = messages.parseLongOrPrint(scanner.nextLine().trim());

        try {
            genreService.getGenreById(id);
        } catch (InvalidIdFormatException | BookDoesNotExistException e) {
            System.out.println(e.getMessage());
            return -1;
        }

        return id.getAsLong();
    }
}
