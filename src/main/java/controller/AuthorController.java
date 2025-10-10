package controller;

import main.Main;
import org.springframework.stereotype.Component;
import service.AuthorService;

import java.util.ResourceBundle;
import java.util.Scanner;

@Component
public class AuthorController {
    private final AuthorService authorService;
    private ResourceBundle messages;
    private final Scanner scanner = new Scanner(System.in);

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    public void showMenu(){
        boolean running = true;
        messages = Main.getMessages();

        while (running) {
            System.out.println(messages.getString("author.menu.select"));
            System.out.println(messages.getString("author.menu.create"));
            System.out.println(messages.getString("author.menu.read"));
            System.out.println(messages.getString("author.menu.update"));
            System.out.println(messages.getString("author.menu.delete"));
            System.out.println(messages.getString("author.menu.quit"));

            String choice = scanner.nextLine().trim();

            try {
                int choiceInt = Integer.parseInt(choice);

                switch (choiceInt) {
                    case 1 -> createAuthor();
                    case 2 -> readAuthors();
                    case 3 -> updateAuthor();
                    case 4 -> deleteAuthor();
                    case 5 -> running = false;
                    default -> throw new Exception();
                }
            } catch(Exception e) {
                System.out.println(messages.getString("author.menu.invalid.option"));
            }
        }
    }

    public void createAuthor(){
        boolean running = true;

        while (running) {
            System.out.println(messages.getString("author.create.firstName"));

            String firstName = scanner.nextLine().trim();

            if (firstName.isEmpty()) {
                System.out.println(messages.getString("author.create.invalid.firstName"));
                continue;
            }

            System.out.println(messages.getString("author.create.lastName"));

            String lastName = scanner.nextLine().trim();

            if (lastName.isEmpty()) {
                System.out.println(messages.getString("author.create.invalid.lastName"));
                continue;
            }

            authorService.createAuthor(firstName, lastName);
            running = false;
        }
    }

    public void readAuthors(){
        if (authorService.getAllAuthors().isEmpty()) {
            System.out.println(messages.getString("author.read.noAuthors"));
        }

        authorService.getAllAuthors().forEach(System.out::println);
    }

    public void updateAuthor(){
        readAuthors();

        boolean running = true;

        while (running) {
            System.out.println(messages.getString("author.update.select"));

            String choice = scanner.nextLine().trim();

            try {
                int choiceInt = Integer.parseInt(choice);

                if (authorService.getAuthorById(choiceInt) == null) {
                    throw new IllegalArgumentException();
                }
            } catch (Exception e) {
                System.out.println(messages.getString("author.update.invalid"));
            }

            System.out.println(messages.getString("author.update.firstName"));

            String firstName = scanner.nextLine().trim();

            if (firstName.isEmpty()) {
                System.out.println(messages.getString("author.update.invalid.firstName"));
                continue;
            }

            System.out.println(messages.getString("author.update.lastName"));

            String lastName = scanner.nextLine().trim();

            if (lastName.isEmpty()) {
                System.out.println(messages.getString("author.update.invalid.lastName"));
                continue;
            }

            authorService.updateAuthor(firstName, lastName);
            running = false;
        }
    }

    public void deleteAuthor(){
        readAuthors();

        System.out.println(messages.getString("author.delete.select"));

        String choice = scanner.nextLine().trim();

        try {
            int choiceInt = Integer.parseInt(choice);
            authorService.deleteAuthor(choiceInt);
            System.out.println(messages.getString("author.delete.successful"));
        } catch(Exception e) {
            System.out.println(messages.getString("author.delete.invalid"));
        }
    }
}
