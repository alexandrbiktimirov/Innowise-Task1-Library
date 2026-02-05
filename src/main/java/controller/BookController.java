package controller;

import command.*;
import i18n.LocaleHolder;
import i18n.Messages;

import java.util.*;

public class BookController {
    private final Map<Integer, Command> commands;
    private final Scanner scanner;
    private final Messages messages;
    private final LocaleHolder localeHolder;

    public BookController(Map<Integer, Command> commands, Scanner scanner, Messages messages, LocaleHolder localeHolder) {
        this.commands = commands;
        this.scanner = scanner;
        this.messages = messages;
        this.localeHolder = localeHolder;
    }

    public void start() {
        chooseLanguage();

        while (true) {
            System.out.println(messages.get("start.select"));
            System.out.println(messages.get("start.display"));
            System.out.println(messages.get("start.create"));
            System.out.println(messages.get("start.update"));
            System.out.println(messages.get("start.delete"));
            System.out.println(messages.get("start.quit"));

            OptionalInt opt = messages.parseIntOrPrint(scanner.nextLine().trim());
            if (opt.isEmpty()) continue;

            Command cmd = commands.get(opt.getAsInt());
            if (cmd == null) {
                System.out.println(messages.get("start.invalid.option"));
                continue;
            }

            cmd.execute();
        }
    }

    private void chooseLanguage() {
        while (true) {
            System.out.println("Please select the language / Poproszę wybrać język:");
            System.out.println("1. English");
            System.out.println("2. Polski");

            try {
                int id = Integer.parseInt(scanner.nextLine().trim());
                Locale locale = switch (id) {
                    case 1 -> Locale.ENGLISH;
                    case 2 -> new Locale("pl");
                    default -> throw new IllegalArgumentException();
                };

                localeHolder.setLocale(locale);
                break;
            } catch (Exception e) {
                System.out.println("Invalid input, please try again / Niepoprawna opcja, poproszę powtórzyć");
            }
        }
    }
}