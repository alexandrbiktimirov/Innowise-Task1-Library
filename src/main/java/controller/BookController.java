package controller;

import org.springframework.stereotype.Controller;
import service.BookService;

import java.util.Scanner;

@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void start(){
        boolean quit = false;

        while (!quit) {
            System.out.println("Please select one of the following options: ");
            System.out.println("1 - Display book list");
            System.out.println("2 - Create a new book");
            System.out.println("3 - Delete a book");
            System.out.println("4 - Update a book");
            System.out.println("5 - Quit the program");

            Scanner scanner = new Scanner(System.in);

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    bookService.readAllBooks().forEach(System.out::println);
                    break;
                case 5:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }
}
