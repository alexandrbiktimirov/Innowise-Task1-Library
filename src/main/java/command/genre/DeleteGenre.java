package command.genre;

import command.Command;
import exception.GenreDoesNotExistException;
import i18n.Messages;
import org.springframework.stereotype.Component;
import service.GenreService;

import java.util.OptionalLong;
import java.util.Scanner;

@Component
public class DeleteGenre implements Command {
    private final Scanner scanner;
    private final GenreService genreService;
    private final Messages messages;

    public DeleteGenre(Scanner scanner, Messages messages, GenreService genreService) {
        this.scanner = scanner;
        this.messages = messages;
        this.genreService = genreService;
    }
    
    @Override
    public void execute() {
        genreService.getAllGenres().forEach(System.out::println);
        System.out.println(messages.get("genre.delete.id"));

        String inputId = scanner.nextLine().trim();
        OptionalLong id = messages.parseLongOrPrint(inputId);

        if (id.isEmpty()) {
            System.out.println(messages.get("genre.notfound"));
            return;
        }

        try {
            genreService.deleteGenre(id.getAsLong());
            System.out.println(messages.get("genre.delete.successful"));
        } catch (GenreDoesNotExistException e) {
            System.out.println(messages.get("genre.notfound"));
        }
    }
}
