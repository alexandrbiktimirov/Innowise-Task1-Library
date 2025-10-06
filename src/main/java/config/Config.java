package config;

import controller.BookController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import repository.BookRepository;
import service.BookService;
import service.BookServiceImpl;

@Configuration
@Import({JdbcConfig.class, AopConfig.class})
public class Config {

    @Bean
    public BookServiceImpl bookService(BookRepository bookRepository) {
        return new BookServiceImpl(bookRepository);
    }

    @Bean
    public BookController bookController(BookService bookService){
        return new BookController(bookService);
    }
}
