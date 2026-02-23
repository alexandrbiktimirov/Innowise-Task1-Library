package controller;

import command.Command;
import i18n.Messages;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.OptionalInt;
import java.util.Scanner;

@Component
public class BookController {
    private final Scanner scanner;
    private final Messages messages;
    private final Map<Integer, Command> bookCommands;

    public BookController(Scanner scanner, Messages messages, @Qualifier("bookCommands") Map<Integer, Command> bookCommands) {
        this.scanner = scanner;
        this.messages = messages;
        this.bookCommands = bookCommands;
    }

    public void showMenu() {
        while (true) {
            System.out.println(messages.get("book.menu.display"));
            System.out.println(messages.get("book.menu.create"));
            System.out.println(messages.get("book.menu.update"));
            System.out.println(messages.get("book.menu.delete"));
            System.out.println(messages.get("book.menu.return"));

            OptionalInt choice = messages.parseIntOrPrint(scanner.nextLine().trim());
            if (choice.isEmpty()) {
                System.out.println(messages.get("book.menu.invalid"));
                continue;
            }

            Command cmd = bookCommands.get(choice.getAsInt());
            if (cmd == null) {
                System.out.println(messages.get("book.menu.command.invalid"));
                continue;
            }

            cmd.execute();
        }
    }
}