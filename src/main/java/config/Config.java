package config;

import aop.CachingAspect;
import aop.LoggingAspect;
import controller.BookController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import service.BookService;
import service.BookServiceImpl;

@Configuration
@EnableAspectJAutoProxy
public class Config {

    @Bean
    public CachingAspect cachingAspect() {
        return new CachingAspect();
    }

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

    @Bean
    public BookServiceImpl bookService(){
        return new BookServiceImpl();
    }

    @Bean
    public BookController bookController(BookService bookService){
        return new BookController(bookService);
    }
}
