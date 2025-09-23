package service;

import model.Book;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.TreeSet;

@Service
public class BookService {
    private final TreeSet<Book> books = new TreeSet<>();

    public BookService() {
        loadFromFile();
    }

    private void loadFromFile() {

        try (var lines = Files.lines(Path.of("csv/books.csv"))
        ){
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

    public Set<Book> readAllBooks() {
        return books;
    }

    public void createBook(String title, String author, String description) {
        int id = books.last().getId() + 1;

        books.add(new Book(id, title, author, description));
    }

    public void updateBook(int id, String title, String author, String description) {

    }

    public void deleteBook(int id) {

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
