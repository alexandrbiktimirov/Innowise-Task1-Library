package command.genre;

import command.Command;
import i18n.Messages;
import org.springframework.stereotype.Component;
import service.GenreService;

@Component
public class DisplayAllGenres implements Command {
    private final GenreService genreService;
    private final Messages messages;

    public DisplayAllGenres(GenreService genreService,  Messages messages) {
        this.genreService = genreService;
        this.messages = messages;
    }

    @Override
    public void execute() {
        var genres = genreService.getAllGenres();

        if (genres.isEmpty()) {
            System.out.println(messages.get("genre.empty"));
        } else{
            genres.forEach(System.out::println);
        }
    }
}