package service;

import aop.Cached;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import exception.BookDoesNotExistException;
import jakarta.annotation.PostConstruct;
import model.Book;
import org.springframework.stereotype.Service;
import jakarta.annotation.PreDestroy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final List<Book> books = new ArrayList<>();
    private final List<Book> initialBooks = new ArrayList<>();


    @PostConstruct
    private void loadFromFile() {
        File csvFile = new File("csv/books.csv");

        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper
                .schemaFor(Book.class)
                .withHeader()
                .withColumnSeparator(',');

        try (MappingIterator<Book> iterator = mapper.readerFor(Book.class).with(schema).readValues(csvFile)){
            while(iterator.hasNext()){
                Book book = iterator.next();
                books.add(book);
                initialBooks.add(book);
            }
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Cached
    public Book getBookById(int id) throws BookDoesNotExistException {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElseThrow(BookDoesNotExistException::new);
    }

    @Override
    public List<Book> readAllBooks() {
        return books;
    }

    @Override
    public void createBook(String title, String author, String description) {
        int id = books.isEmpty() ? 1 : books.getLast().getId() + 1;

        books.add(new Book(id, title, author, description));
    }

    @Override
    public void updateBookTitle(int id, String newTitle) throws BookDoesNotExistException {
        Book book = getBookById(id);
        book.setTitle(newTitle);
    }

    @Override
    public void updateBookAuthor(int id, String newAuthor) throws BookDoesNotExistException {
        Book book = getBookById(id);
        book.setAuthor(newAuthor);
    }

    @Override
    public void updateBookDescription(int id, String newDescription) throws BookDoesNotExistException {
        Book book = getBookById(id);
        book.setDescription(newDescription);
    }

    @Override
    public void updateBook(int id, String newTitle, String newAuthor, String newDescription) throws BookDoesNotExistException {
        Book book = getBookById(id);
        book.setTitle(newTitle);
        book.setAuthor(newAuthor);
        book.setDescription(newDescription);
    }

    @Override
    public void deleteBook(int id) throws BookDoesNotExistException {
        Book book = getBookById(id);
        books.remove(book);
    }

    @PreDestroy
    private void writeChangesToFile() {
        if (books.equals(initialBooks)) {
            return;
        }

        File csvFile = new File("csv/books.csv");
        CsvMapper mapper = new CsvMapper();

        CsvSchema schema = mapper
                .schemaFor(Book.class)
                .withHeader()
                .withColumnSeparator(',');

        try {
            mapper.writerFor(
                    mapper.getTypeFactory().constructParametricType(java.util.List.class, Book.class)
                    )
                    .with(schema)
                    .writeValue(csvFile, books);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
