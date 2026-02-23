package controller;

import command.Command;
import i18n.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.OptionalInt;
import java.util.Scanner;

@Component
public class AuthorController {
    private final Scanner scanner;
    private final Messages messages;
    private final Map<Integer, Command> authorCommands;

    public AuthorController(Scanner scanner, Messages messages, @Qualifier("authorCommands") Map<Integer, Command> authorCommands) {
        this.scanner = scanner;
        this.messages = messages;
        this.authorCommands = authorCommands;
    }

    public void showMenu() {
        while (true) {
            System.out.println(messages.get("author.menu.display"));
            System.out.println(messages.get("author.menu.create"));
            System.out.println(messages.get("author.menu.update"));
            System.out.println(messages.get("author.menu.delete"));
            System.out.println(messages.get("author.menu.return"));

            OptionalInt choice = messages.parseIntOrPrint(scanner.nextLine().trim());
            if (choice.isEmpty()) {
                System.out.println(messages.get("author.menu.invalid"));
                continue;
            }

            Command cmd = authorCommands.get(choice.getAsInt());
            if (cmd == null) {
                System.out.println(messages.get("author.menu.command.invalid"));
                continue;
            }

            cmd.execute();
        }
    }
}
