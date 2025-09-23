package service;

import model.Book;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.TreeSet;

@Service
public class BookService {
    private final Set<Book> books = new TreeSet<>();

    public BookService() {
        loadFromFile();
    }

    private void loadFromFile() {
        var resource = new ClassPathResource("books.csv");

        try (var in = resource.getInputStream();
             var reader = new BufferedReader(new InputStreamReader(in))
        ){
            reader.lines().forEach(line -> {
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

    }

    public void updateBook(String title, String author, String description) {

    }

    public void deleteBook(int id) {

    }
}
