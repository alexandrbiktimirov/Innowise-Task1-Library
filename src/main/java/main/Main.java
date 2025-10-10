package main;

import config.Config;
import controller.AuthorController;
import controller.BookController;
import controller.GenreController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Main {
    private final static Scanner scanner = new Scanner(System.in);
    private static ResourceBundle messages;

    public static void chooseLanguage() {
        boolean languageChosen = false;

        while (!languageChosen) {
            System.out.println("Please select the language/Poproszę wybrać język:");
            System.out.println("1. English");
            System.out.println("2. Polski");

            String choice = scanner.nextLine().trim();
            Locale locale;

            try {
                int id = Integer.parseInt(choice);

                switch (id) {
                    case 1 -> locale = Locale.ENGLISH;
                    case 2 -> locale = new Locale("pl");
                    default -> throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("Invalid input, please try again / Niepoprawna opcja, poproszę powtórzyć");
                continue;
            }

            messages = ResourceBundle.getBundle("messages", locale);
            languageChosen = true;
        }
    }

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(Config.class)) {
            chooseLanguage();

            boolean quit = false;

            while (!quit) {
                System.out.println(messages.getString("start.select"));
                System.out.println(messages.getString("start.book"));
                System.out.println(messages.getString("start.author"));
                System.out.println(messages.getString("start.genre"));
                System.out.println(messages.getString("start.quit"));

                String input = scanner.nextLine().trim();
                try {
                    int option = Integer.parseInt(input);

                    switch (option) {
                        case 1 -> context.getBean(BookController.class).showMenu();
                        case 2 -> context.getBean(AuthorController.class).showMenu();
                        case 3 -> context.getBean(GenreController.class).showMenu();
                        case 4 -> quit = true;
                        default -> System.out.println(messages.getString("start.invalid.option"));
                    }
                } catch (NumberFormatException e) {
                    System.out.println(messages.getString("start.invalid.option"));
                }
            }
        }
    }

    public static ResourceBundle getMessages() {
        return messages;
    }
}
