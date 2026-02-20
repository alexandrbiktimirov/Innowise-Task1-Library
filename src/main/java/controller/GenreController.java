package controller;

import command.genre.GenreCommand;
import i18n.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.OptionalInt;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class GenreController {
    private final Scanner scanner;
    private final Messages messages;
    private final Map<Integer, GenreCommand> genreCommands;

    public void showMenu() {
        while (true) {
            System.out.println(messages.get("genre.menu.display"));
            System.out.println(messages.get("genre.menu.create"));
            System.out.println(messages.get("genre.menu.update"));
            System.out.println(messages.get("genre.menu.delete"));
            System.out.println(messages.get("genre.menu.return"));

            OptionalInt choice = messages.parseIntOrPrint(scanner.nextLine().trim());
            if (choice.isEmpty()) {
                System.out.println(messages.get("genre.menu.invalid"));
                continue;
            }

            GenreCommand cmd = genreCommands.get(choice.getAsInt());
            if (cmd == null) {
                System.out.println(messages.get("genre.menu.command.invalid"));
                continue;
            }

            cmd.execute();
        }
    }
}