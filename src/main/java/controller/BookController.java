package controller;

import command.book.BookCommand;
import i18n.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.OptionalInt;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class BookController {
    private final Scanner scanner;
    private final Messages messages;
    private final Map<Integer, BookCommand> bookCommands;

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

            BookCommand cmd = bookCommands.get(choice.getAsInt());
            if (cmd == null) {
                System.out.println(messages.get("book.menu.command.invalid"));
                continue;
            }

            cmd.execute();
        }
    }
}