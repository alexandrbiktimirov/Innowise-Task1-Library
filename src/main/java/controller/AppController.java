package controller;

import org.springframework.stereotype.Controller;

@Controller
public class AppController {


    public void start() {
        chooseLanguage();

        boolean quit = false;

        while (!quit) {
            System.out.println(messages.getString("start.select"));
            System.out.println(messages.getString("start.display"));
            System.out.println(messages.getString("start.create"));
            System.out.println(messages.getString("start.update"));
            System.out.println(messages.getString("start.delete"));
            System.out.println(messages.getString("start.quit"));

            String input = scanner.nextLine().trim();
            try {
                int option = Integer.parseInt(input);

                switch (option) {
                    case 1 -> displayAllBooks();
                    case 2 -> createNewBook();
                    case 3 -> updateBook();
                    case 4 -> deleteBook();
                    case 5 -> quit = true;
                    default -> System.out.println(messages.getString("start.invalid.option"));
                }
            } catch (NumberFormatException e) {
                System.out.println(messages.getString("start.invalid.option"));
            }
        }
    }

    public void chooseLanguage() {
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
}
