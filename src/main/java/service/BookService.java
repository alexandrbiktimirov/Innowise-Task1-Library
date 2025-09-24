package service;

import exception.BookDoesNotExistException;
import model.Book;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();

    public BookService() {
        loadFromFile();
    }

    private void loadFromFile() {
        try (var lines = Files.lines(Path.of("csv/books.csv"))){
            lines.forEach(line -> {
                String[] split = line.split(",");
                int id = Integer.parseInt(split[0].trim());
                String title = split[1].trim();
                String author = split[2].trim();
                String description = split[3].trim();

                books.add(new Book(id, title, author, description));
            });
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public Book getBookById(int id) throws BookDoesNotExistException {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(() -> new BookDoesNotExistException("Book with id " + id + " does not exist"));
    }

    public List<Book> readAllBooks() {
        return books;
    }

    public void createBook(String title, String author, String description) {
        int id = books.getLast().getId() + 1;

        books.add(new Book(id, title, author, description));
    }

    public void updateBookTitle(int id, String newTitle) throws BookDoesNotExistException {
        Book book = getBookById(id);
        book.setTitle(newTitle);
    }

    public void updateBookAuthor(int id, String newAuthor) throws BookDoesNotExistException {
        Book book = getBookById(id);
        book.setAuthor(newAuthor);
    }

    public void updateBookDescription(int id, String newDescription) throws BookDoesNotExistException {
        Book book = getBookById(id);
        book.setDescription(newDescription);
    }

    public void updateBook(int id, String newTitle, String newAuthor, String newDescription) throws BookDoesNotExistException {
        Book book = getBookById(id);
        book.setTitle(newTitle);
        book.setAuthor(newAuthor);
        book.setDescription(newDescription);
    }

    public void deleteBook(int id) throws BookDoesNotExistException {
        Book book = getBookById(id);
        books.remove(book);
    }

    public void writeChangesToFile() {
        try (var writer = new FileWriter("csv/books.csv")) {
            for (Book book : books) {
                writer.write(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getDescription() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
