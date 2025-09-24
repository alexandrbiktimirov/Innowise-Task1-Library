package controller;

import exception.BookDoesNotExistException;
import model.Book;
import org.springframework.stereotype.Controller;
import service.BookService;

import java.util.Scanner;

@Controller
public class BookController {
    private final BookService bookService;
    private final Scanner scanner = new Scanner(System.in);

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void start() {
        boolean quit = false;

        while (!quit) {
            System.out.println("Please select one of the following options (enter only a number): ");
            System.out.println("1 - Display book list");
            System.out.println("2 - Create a new book");
            System.out.println("3 - Update a book");
            System.out.println("4 - Delete a book");
            System.out.println("5 - Quit the program");

            String input = scanner.nextLine().trim();
            try {
                int option = Integer.parseInt(input);

                switch (option) {
                    case 1 -> displayAllBooks();
                    case 2 -> createNewBook();
                    case 3 -> updateBook();
                    case 4 -> deleteBook();
                    case 5 -> quit = true;
                    default -> System.out.println("Invalid option");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Please enter a number from 1 to 5");
            }
        }

        bookService.writeChangesToFile();
    }

    public void displayAllBooks() {
        bookService.readAllBooks().forEach(System.out::println);
    }

    public void createNewBook() {
        boolean correctData = false;

        while (!correctData) {
            System.out.println("Please enter the name of the book: ");
            String name = scanner.nextLine().trim();

            if (name.contains(",")) {
                System.out.println("Invalid name of the book, please try again");
                continue;
            }

            System.out.println("Please enter the author of the book: ");
            String author = scanner.nextLine().trim();

            if (author.contains(",")) {
                System.out.println("Invalid author of the book, please try again");
                continue;
            }

            System.out.println("Please enter the description the book: ");
            String description = scanner.nextLine().trim();

            if (description.contains(",")) {
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
        int id;

        try{
            id = Integer.parseInt(inputId);
        } catch (NumberFormatException e){
            System.out.println("Invalid id entered, please try again");
            return;
        }

        System.out.println("What would you like to edit?");
        System.out.println("1 - Title");
        System.out.println("2 - Author");
        System.out.println("3 - Description");
        System.out.println("4 - Everything");

        String inputOption = scanner.nextLine().trim();
        int option;
        try {
            option = Integer.parseInt(inputOption);
        } catch (NumberFormatException e){
            System.out.println("Invalid option entered, please try again");
            return;
        }

        switch (option) {
            case 1 -> {
                System.out.println("Enter the new title of the book: ");
                String newTitle = scanner.next().trim();

                try {
                    bookService.updateBookTitle(id, newTitle);
                    System.out.println("Title updated successfully");
                } catch (BookDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
            case 2 -> {
                System.out.println("Enter the new author of the book: ");
                String newAuthor = scanner.next().trim();

                try {
                    bookService.updateBookAuthor(id, newAuthor);
                    System.out.println("Author updated successfully");
                } catch (BookDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
            case 3 -> {
                System.out.println("Enter the new description of the book: ");
                String newDescription = scanner.next().trim();

                try {
                    bookService.updateBookDescription(id, newDescription);
                    System.out.println("Description updated successfully");
                } catch (BookDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
            case 4 -> {
                System.out.println("Enter the new title of the book: ");
                String newTitle = scanner.next().trim();
                System.out.println("Enter the new author of the book: ");
                String newAuthor = scanner.next().trim();
                System.out.println("Enter the new description of the book: ");
                String newDescription = scanner.next().trim();

                try {
                    bookService.updateBook(id, newTitle, newAuthor, newDescription);
                    System.out.println("Book updated successfully");
                } catch (BookDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
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
        } catch (NumberFormatException e){
            System.out.println("Invalid id entered, please try again");
            return;
        }

        try {
            bookService.deleteBook(id);
            System.out.println("Book deleted successfully");
        } catch (BookDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }
}