package controller;

import command.*;
import exception.BookDoesNotExistException;
import service.BookService;

import java.util.Map;
import java.util.Scanner;

public class BookController {
    private final BookService bookService;
    private final Scanner scanner = new Scanner(System.in);

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void start() {
        var context = new CommandsContext(scanner, bookService);
        Map<Integer, Command> commands = Map.of(
                1, new DisplayAllBooks(context),
                2, new CreateNewBook(context),
                3, new UpdateBook(context),
                4, new DeleteBook(context),
                5, new QuitProgram(context)
        );

        while (true) {
            System.out.println("Please select one of the following options (enter only a number): ");
            System.out.println("1 - Display book list");
            System.out.println("2 - Create a new book");
            System.out.println("3 - Update a book");
            System.out.println("4 - Delete a book");
            System.out.println("5 - Quit the program");

            String input = scanner.nextLine().trim();
            int option = isFormatValid(input);

            var command = commands.get(option);

            if (command == null) {
                System.out.println("Invalid option");
                continue;
            }

            command.execute();
        }
    }

    public void displayAllBooks() {
        bookService.readAllBooks().forEach(System.out::println);
    }

    public void createNewBook() {
        boolean correctData = false;

        while (!correctData) {
            System.out.println("Please enter the name of the book: ");
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Invalid name of the book, please try again");
                continue;
            }

            System.out.println("Please enter the author of the book: ");
            String author = scanner.nextLine().trim();

            if (author.isEmpty()) {
                System.out.println("Invalid author of the book, please try again");
                continue;
            }

            System.out.println("Please enter the description the book: ");
            String description = scanner.nextLine().trim();

            if (description.isEmpty()) {
                System.out.println("Invalid description of the book, please try again");
                continue;
            }

            bookService.createBook(name, author, description);
            correctData = true;
        }
    }

    public void updateBook() {
        displayAllBooks();
        System.out.println("Please enter the id of the book you would like to edit: ");

        String inputId = scanner.nextLine().trim();
        int id = isFormatValid(inputId);

        System.out.println("What would you like to edit?");
        System.out.println("1 - Title");
        System.out.println("2 - Author");
        System.out.println("3 - Description");
        System.out.println("4 - Everything");

        String inputOption = scanner.nextLine().trim();
        int option;
        try {
            option = Integer.parseInt(inputOption);
        } catch (NumberFormatException e) {
            System.out.println("Invalid option entered, please try again");
            return;
        }

        switch (option) {
            case 1 -> {
                System.out.println("Enter the new title of the book: ");
                String newTitle = scanner.next().trim();

                executeAction(() -> bookService.updateBookTitle(id, newTitle), "Title updated successfully");
            }
            case 2 -> {
                System.out.println("Enter the new author of the book: ");
                String newAuthor = scanner.next().trim();

                executeAction(() -> bookService.updateBookAuthor(id, newAuthor), "Author updated successfully");
            }
            case 3 -> {
                System.out.println("Enter the new description of the book: ");
                String newDescription = scanner.next().trim();

                executeAction(() -> bookService.updateBookDescription(id, newDescription), "Description updated successfully");
            }
            case 4 -> {
                System.out.println("Enter the new title of the book: ");
                String newTitle = scanner.next().trim();
                System.out.println("Enter the new author of the book: ");
                String newAuthor = scanner.next().trim();
                System.out.println("Enter the new description of the book: ");
                String newDescription = scanner.next().trim();

                executeAction(() -> bookService.updateBook(id, newTitle, newAuthor, newDescription), "Book updated successfully");
            }
            default -> System.out.println("Invalid option. Please try again");
        }
    }

    public void deleteBook() {
        displayAllBooks();
        System.out.println("Please enter the id of the book you would like to delete: ");

        String inputId = scanner.nextLine().trim();
        int id;
        try {
            id = Integer.parseInt(inputId);
        } catch (NumberFormatException e) {
            System.out.println("Invalid id entered, please try again");
            return;
        }

        executeAction(() -> bookService.deleteBook(id), "Book deleted successfully");
    }

    public static void executeAction(Runnable action, String message) {
        try {
            action.run();
            System.out.println(message);
        } catch (BookDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int isFormatValid(String id) {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid id entered, please try again");
            return -1;
        }
    }
}