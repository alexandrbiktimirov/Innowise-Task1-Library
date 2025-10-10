package controller;

import main.Main;
import org.springframework.stereotype.Component;
import service.GenreService;

import java.util.ResourceBundle;
import java.util.Scanner;

@Component
public class GenreController {
    private final GenreService genreService;
    private ResourceBundle messages;
    private final Scanner scanner = new Scanner(System.in);

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    public void showMenu() {
        boolean running = true;
        messages = Main.getMessages();

        while (running) {
            System.out.println(messages.getString("genre.menu.select"));
            System.out.println(messages.getString("genre.menu.create"));
            System.out.println(messages.getString("genre.menu.read"));
            System.out.println(messages.getString("genre.menu.update"));
            System.out.println(messages.getString("genre.menu.delete"));
            System.out.println(messages.getString("genre.menu.quit"));

            String choice = scanner.nextLine().trim();

            try {
                int choiceInt = Integer.parseInt(choice);

                switch (choiceInt) {
                    case 1 -> createGenre();
                    case 2 -> readGenres();
                    case 3 -> updateGenre();
                    case 4 -> deleteGenre();
                    case 5 -> running = false;
                    default -> System.out.println(messages.getString("genre.menu.invalid.option"));
                }
            } catch (NumberFormatException e) {
                System.out.println(messages.getString("genre.menu.invalid.option"));
            }
        }
    }

    public void createGenre() {
        boolean running = true;

        while (running) {
            System.out.println(messages.getString("genre.create.name"));
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println(messages.getString("genre.create.invalid.name"));
                continue;
            }

            genreService.createGenre(name);
            running = false;
        }
    }

    public void readGenres() {
        if (genreService.getAllGenres().isEmpty()) {
            System.out.println(messages.getString("genre.read.noGenres"));
        }
        genreService.getAllGenres().forEach(System.out::println);
    }

    public void updateGenre() {
        readGenres();
        boolean running = true;

        while (running) {
            System.out.println(messages.getString("genre.update.select"));
            String idStr = scanner.nextLine().trim();
            long id;
            try {
                id = Long.parseLong(idStr);
                if (genreService.getGenreById(id) == null) {
                    throw new IllegalArgumentException();
                }
            } catch (Exception e) {
                System.out.println(messages.getString("genre.update.invalid"));
                continue;
            }

            System.out.println(messages.getString("genre.update.name"));
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println(messages.getString("genre.update.invalid.name"));
                continue;
            }

            genreService.updateGenre(id, name);
            running = false;
        }
    }

    public void deleteGenre() {
        readGenres();

        System.out.println(messages.getString("genre.delete.select"));
        String idStr = scanner.nextLine().trim();

        try {
            long id = Long.parseLong(idStr);
            genreService.deleteGenre(id);
            System.out.println(messages.getString("genre.delete.successful"));
        } catch (Exception e) {
            System.out.println(messages.getString("genre.delete.invalid"));
        }
    }
}