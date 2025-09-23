package config;

import controller.BookController;
import model.Book;
import org.springframework.context.annotation.Bean;
import service.BookService;

@org.springframework.context.annotation.Configuration
public class Config {

    @Bean
    public Book book(){
        return new Book();
    }

    @Bean
    public BookService bookService(){
        return new BookService();
    }

    @Bean
    public BookController bookController(BookService bookService){
        return new BookController(bookService);
    }
}
