package config;

import aop.LoggingAspect;
import controller.BookController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import service.BookService;

@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class Config {

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
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
