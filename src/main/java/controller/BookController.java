package controller;

import exception.BookDoesNotExistException;
import model.Book;
import org.springframework.stereotype.Controller;
import service.BookService;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

@Controller
public class BookController {
    private final BookService bookService;
    private final Scanner scanner = new Scanner(System.in);
    private ResourceBundle messages;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

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

        bookService.writeChangesToFile();
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

    public void displayAllBooks() {
        List<Book> books = bookService.readAllBooks();

        if (books.isEmpty()){
            System.out.println(messages.getString("books.empty"));
        }

        bookService.readAllBooks().forEach(System.out::println);
    }

    public void createNewBook() {
        boolean correctData = false;

        while (!correctData) {
            System.out.println(messages.getString("create.name"));
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println(messages.getString("create.invalid.name"));
                continue;
            }

            System.out.println(messages.getString("create.author"));
            String author = scanner.nextLine().trim();

            if (author.isEmpty()) {
                System.out.println(messages.getString("create.invalid.author"));
                continue;
            }

            System.out.println(messages.getString("create.description"));
            String description = scanner.nextLine().trim();

            if (description.isEmpty()) {
                System.out.println(messages.getString("create.invalid.description"));
                continue;
            }

            bookService.createBook(name, author, description);
            correctData = true;
        }
    }

    public void updateBook() {
        displayAllBooks();
        System.out.println(messages.getString("update.id"));

        String inputId = scanner.nextLine().trim();
        int id;

        try{
            id = Integer.parseInt(inputId);
        } catch (NumberFormatException e){
            System.out.println(messages.getString("update.invalid.id"));
            return;
        }

        System.out.println(messages.getString("update.options"));
        System.out.println(messages.getString("update.options.title"));
        System.out.println(messages.getString("update.options.author"));
        System.out.println(messages.getString("update.options.description"));
        System.out.println(messages.getString("update.options.everything"));

        String inputOption = scanner.nextLine().trim();
        int option;
        try {
            option = Integer.parseInt(inputOption);
        } catch (NumberFormatException e){
            System.out.println(messages.getString("update.invalid.option"));
            return;
        }

        switch (option) {
            case 1 -> {
                System.out.println(messages.getString("update.title"));
                String newTitle = scanner.next().trim();

                try {
                    bookService.updateBookTitle(id, newTitle);
                    System.out.println(messages.getString("update.successful.title"));
                } catch (BookDoesNotExistException e) {
                    System.out.println(messages.getString("book.exception.first") + id + messages.getString("book.exception.second"));
                }
            }
            case 2 -> {
                System.out.println(messages.getString("update.author"));
                String newAuthor = scanner.next().trim();

                try {
                    bookService.updateBookAuthor(id, newAuthor);
                    System.out.println(messages.getString("update.successful.author"));
                } catch (BookDoesNotExistException e) {
                    System.out.println(messages.getString("book.exception.first") + id + messages.getString("book.exception.second"));
                }
            }
            case 3 -> {
                System.out.println(messages.getString("update.description"));
                String newDescription = scanner.next().trim();

                try {
                    bookService.updateBookDescription(id, newDescription);
                    System.out.println(messages.getString("update.successful.description"));
                } catch (BookDoesNotExistException e) {
                    System.out.println(messages.getString("book.exception.first") + id + messages.getString("book.exception.second"));
                }
            }
            case 4 -> {
                System.out.println(messages.getString("update.title"));
                String newTitle = scanner.next().trim();
                System.out.println(messages.getString("update.author"));
                String newAuthor = scanner.next().trim();
                System.out.println(messages.getString("update.description"));
                String newDescription = scanner.next().trim();

                try {
                    bookService.updateBook(id, newTitle, newAuthor, newDescription);
                    System.out.println(messages.getString("update.successful.everything"));
                } catch (BookDoesNotExistException e) {
                    System.out.println(messages.getString("book.exception.first") + id + messages.getString("book.exception.second"));
                }
            }
            default -> System.out.println(messages.getString("update.invalid.option"));
        }
    }

    public void deleteBook() {
        displayAllBooks();
        System.out.println(messages.getString("delete.id"));

        String inputId = scanner.nextLine().trim();
        int id;
        try {
            id = Integer.parseInt(inputId);
        } catch (NumberFormatException e){
            System.out.println(messages.getString("delete.invalid.id"));
            return;
        }

        try {
            bookService.deleteBook(id);
            System.out.println(messages.getString("delete.successful"));
        } catch (BookDoesNotExistException e) {
            System.out.println(messages.getString("book.exception.first") + id + messages.getString("book.exception.second"));
        }
    }
}