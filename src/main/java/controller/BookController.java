package controller;

import command.*;
import exception.BookDoesNotExistException;
import service.BookService;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

public class BookController {
    private final BookService bookService;

    private static ResourceBundle messages;
    private final Scanner scanner = new Scanner(System.in);

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void start() {
        chooseLanguage();

        var context = new CommandsContext(scanner, bookService, messages);
        Map<Integer, Command> commands = Map.of(
                1, new DisplayAllBooks(context),
                2, new CreateNewBook(context),
                3, new UpdateBook(context),
                4, new DeleteBook(context),
                5, new QuitProgram(context)
        );

        while (true) {
            System.out.println(messages.getString("start.select"));
            System.out.println(messages.getString("start.display"));
            System.out.println(messages.getString("start.create"));
            System.out.println(messages.getString("start.update"));
            System.out.println(messages.getString("start.delete"));
            System.out.println(messages.getString("start.quit"));

            String input = scanner.nextLine().trim();
            int option = isFormatValid(input);

            var command = commands.get(option);

            if (command == null) {
                System.out.println("start.invalid.option");
                continue;
            }

            command.execute();
        }
    }

    private void chooseLanguage() {
        while (true) {
            System.out.println("Please select the language / Poproszę wybrać język:");
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
            break;
        }
    }

    public static void executeAction(Runnable action, String message) {
        try {
            action.run();
            System.out.println(message);
        } catch (BookDoesNotExistException e) {
            System.out.println(messages.getString("book.exception"));
        }
    }

    public static int isFormatValid(String id) {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            System.out.println(messages.getString("invalid.id"));
            return -1;
        }
    }
}