package command.genre;

import i18n.Messages;
import org.springframework.stereotype.Component;
import service.GenreService;

@Component
public class DisplayAllGenres implements GenreCommand {
    private final GenreService genreService;
    private final Messages messages;

    public DisplayAllGenres(GenreService genreService,  Messages messages) {
        this.genreService = genreService;
        this.messages = messages;
    }

    @Override
    public void execute() {
        if (genreService.getAllGenres().isEmpty()) {
            System.out.println(messages.get("genre.empty"));
        }

        genreService.getAllGenres().forEach(System.out::println);
    }
}