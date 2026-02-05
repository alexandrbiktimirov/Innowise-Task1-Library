package command.genre;

import command.Command;
import org.springframework.stereotype.Component;
import service.GenreService;

@Component
public class CreateNewGenre implements Command {

    private final GenreService genreService;

    @Override
    public void execute() {

    }
}
