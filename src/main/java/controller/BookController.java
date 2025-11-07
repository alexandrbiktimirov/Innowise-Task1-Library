package controller;

import main.Main;
import model.Author;
import model.Genre;
import org.springframework.stereotype.Component;
import service.AuthorService;
import service.BookService;
import service.GenreService;

import java.util.ResourceBundle;
import java.util.Scanner;

@Component
public class BookController {
    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;
    private ResourceBundle messages;
    private final Scanner scanner = new Scanner(System.in);

    public BookController(BookService bookService, GenreService genreService, AuthorService authorService) {
        this.bookService = bookService;
        this.genreService = genreService;
        this.authorService = authorService;
    }

    public void showMenu() {
        boolean running = true;
        messages = Main.getMessages();

        while (running) {
            System.out.println(messages.getString("book.menu.select"));
            System.out.println(messages.getString("book.menu.create"));
            System.out.println(messages.getString("book.menu.read"));
            System.out.println(messages.getString("book.menu.update"));
            System.out.println(messages.getString("book.menu.delete"));
            System.out.println(messages.getString("book.menu.quit"));

            String choice = scanner.nextLine().trim();

            try {
                int choiceInt = Integer.parseInt(choice);

                switch (choiceInt) {
                    case 1 -> createBook();
                    case 2 -> readBooks();
                    case 3 -> updateBook();
                    case 4 -> deleteBook();
                    case 5 -> running = false;
                    default -> System.out.println(messages.getString("book.menu.invalid.option"));
                }
            } catch (NumberFormatException e) {
                System.out.println(messages.getString("book.menu.invalid.option"));
            }
        }
    }

    public void createBook() {
        boolean running = true;

        while (running) {
            System.out.println(messages.getString("book.create.title"));
            String title = scanner.nextLine().trim();

            if (title.isEmpty()) {
                System.out.println(messages.getString("book.create.invalid.title"));
                continue;
            }

            System.out.println(messages.getString("book.create.authorId"));
            String authorIdStr = scanner.nextLine().trim();

            Author author;
            try {
                author = authorService.getAuthorById(Long.parseLong(authorIdStr));

                if (author == null) {
                    System.out.println(messages.getString("book.create.invalid.authorId"));
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println(messages.getString("book.create.invalid.authorId"));
                continue;
            }

            System.out.println(messages.getString("book.create.description"));
            String description = scanner.nextLine().trim();
            if (description.isEmpty()) {
                System.out.println(messages.getString("book.create.invalid.description"));
                continue;
            }

            System.out.println(messages.getString("book.create.genreId"));
            String genreIdStr = scanner.nextLine().trim();
            Genre genre;
            try {
                genre = genreService.getGenreById(Long.parseLong(genreIdStr));

                if (genre == null) {
                    System.out.println(messages.getString("book.create.invalid.genreId"));
                    continue;
                }

                bookService.createBook(title, author, description, genre);
                running = false;
            } catch (NumberFormatException e) {
                System.out.println(messages.getString("book.create.invalid.genreId"));
            }
        }
    }

    public void readBooks() {
        if (bookService.getAllBooks().isEmpty()) {
            System.out.println(messages.getString("book.read.noBooks"));
        }
        bookService.getAllBooks().forEach(System.out::println);
    }

    public void updateBook() {
        readBooks();
        boolean running = true;

        while (running) {
            System.out.println(messages.getString("book.update.select"));
            String idStr = scanner.nextLine().trim();
            long id;
            try {
                id = Long.parseLong(idStr);
                if (bookService.getBookById(id) == null) {
                    throw new IllegalArgumentException();
                }
            } catch (Exception e) {
                System.out.println(messages.getString("book.update.invalid"));
                continue;
            }

            System.out.println(messages.getString("book.update.title"));
            String title = scanner.nextLine().trim();
            if (title.isEmpty()) {
                System.out.println(messages.getString("book.update.invalid.title"));
                continue;
            }

            System.out.println(messages.getString("book.update.authorId"));
            String authorIdStr = scanner.nextLine().trim();

            Author author;
            try {
                author = authorService.getAuthorById(Long.parseLong(authorIdStr));

                if (author == null) {
                    System.out.println(messages.getString("book.update.invalid.authorId"));
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println(messages.getString("book.update.invalid.authorId"));
                continue;
            }

            System.out.println(messages.getString("book.update.description"));
            String description = scanner.nextLine().trim();
            if (description.isEmpty()) {
                System.out.println(messages.getString("book.update.invalid.description"));
                continue;
            }

            System.out.println(messages.getString("book.update.genreId"));
            String genreIdStr = scanner.nextLine().trim();

            try {
                Genre genre = genreService.getGenreById(Long.parseLong(genreIdStr));

                if (genre == null) {
                    System.out.println(messages.getString("book.update.invalid.genreId"));
                    continue;
                }

                bookService.updateBook(id, title, author, description, genre);

                running = false;
            } catch (NumberFormatException e) {
                System.out.println(messages.getString("book.update.invalid.genreId"));
            }
        }
    }

    public void deleteBook() {
        readBooks();

        System.out.println(messages.getString("book.delete.select"));
        String idStr = scanner.nextLine().trim();

        try {
            long id = Long.parseLong(idStr);
            bookService.deleteBook(id);
            System.out.println(messages.getString("book.delete.successful"));
        } catch (Exception e) {
            System.out.println(messages.getString("book.delete.invalid"));
        }
    }
}