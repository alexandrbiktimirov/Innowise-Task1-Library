package controller;

import command.QuitProgram;
import i18n.LocaleHolder;
import i18n.Messages;

import java.util.Locale;
import java.util.OptionalInt;
import java.util.Scanner;

public class AppController {
    private final AuthorController authorController;
    private final BookController bookController;
    private final GenreController genreController;
    private final LocaleHolder localeHolder;
    private final Messages messages;
    private final Scanner scanner;
    private final QuitProgram quitProgram;

    public AppController(AuthorController authorController, BookController bookController, GenreController genreController, LocaleHolder localeHolder, Messages messages, Scanner scanner, QuitProgram quitProgram) {
        this.authorController = authorController;
        this.bookController = bookController;
        this.genreController = genreController;
        this.localeHolder = localeHolder;
        this.messages = messages;
        this.scanner = scanner;
        this.quitProgram = quitProgram;
    }

    public void showMenu() {
        while (true) {
            System.out.println(messages.get("app.menu.select"));

            System.out.println(messages.get("app.menu.book"));
            System.out.println(messages.get("app.menu.author"));
            System.out.println(messages.get("app.menu.genre"));
            System.out.println(messages.get("app.menu.quit"));

            OptionalInt option = messages.parseIntOrPrint(scanner.nextLine().trim());

            if (option.isEmpty()) {
                System.out.println(messages.get("common.invalid.number"));
                continue;
            }

            switch (option.getAsInt()) {
                case 1 -> bookController.showMenu();
                case 2 -> authorController.showMenu();
                case 3 -> genreController.showMenu();
                case 4 -> quitProgram.execute();
                default -> System.out.println(messages.get("app.menu.invalid"));
            }
        }
    }

    public void chooseLanguage() {
        while (true) {
            System.out.println("Please select the language/Poproszę wybrać język:");
            System.out.println("1. English");
            System.out.println("2. Polski");

            OptionalInt choice = messages.parseIntOrPrint(scanner.nextLine().trim());

            if (choice.isEmpty()) {
                System.out.println("Invalid input, please try again / Niepoprawna opcja, poproszę powtórzyć");
                continue;
            }

            switch (choice.getAsInt()) {
                case 1 -> localeHolder.setLocale(Locale.ENGLISH);
                case 2 -> localeHolder.setLocale(new Locale("pl"));
                default -> System.out.println("Invalid input, please try again / Niepoprawna opcja, poproszę powtórzyć");
            }

            break;
        }

        showMenu();
    }
}
