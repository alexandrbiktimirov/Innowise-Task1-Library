package controller;

import command.Command;
import i18n.Messages;

import java.util.Map;
import java.util.OptionalInt;
import java.util.Scanner;

public class GenreController {
    private final Scanner scanner;
    private final Messages messages;
    private final Map<Integer, Command> genreCommands;

    public GenreController(Scanner scanner, Messages messages, Map<Integer, Command> genreCommands) {
        this.scanner = scanner;
        this.messages = messages;
        this.genreCommands = genreCommands;
    }

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

            Command cmd = genreCommands.get(choice.getAsInt());
            if (cmd == null) {
                System.out.println(messages.get("genre.menu.command.invalid"));
                continue;
            }

            cmd.execute();
        }
    }
}