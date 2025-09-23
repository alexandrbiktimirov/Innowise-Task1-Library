package controller;

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
            System.out.println("Please select one of the following options: ");
            System.out.println("1 - Display book list");
            System.out.println("2 - Create a new book");
            System.out.println("3 - Delete a book");
            System.out.println("4 - Update a book");
            System.out.println("5 - Quit the program");

            int option = scanner.nextInt();
            switch (option) {
                case 1 -> displayAllBooks();
                case 2 -> createNewBook();
                case 5 -> quit = true;
                default -> System.out.println("Invalid option");
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
            String name = scanner.next();

            if (name == null || name.isEmpty() || name.contains(",")) {
                System.out.println("Invalid name of the book, please try again");
                continue;
            }

            System.out.println("Please enter the author of the book: ");
            String author = scanner.next();

            if (author == null || author.isEmpty() || author.contains(",")) {
                System.out.println("Invalid author of the book, please try again");
                continue;
            }

            System.out.println("Please enter the description the book: ");
            String description = scanner.next();

            if (description == null || description.isEmpty() || description.contains(",")) {
                System.out.println("Invalid description of the book, please try again");
                continue;
            }

            bookService.createBook(name, author, description);
            correctData = true;
        }
    }
}