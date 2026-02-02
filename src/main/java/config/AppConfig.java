package config;

import command.Command;
import controller.BookController;
import i18n.LocaleHolder;
import i18n.Messages;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import service.BookService;

import java.util.Map;
import java.util.Scanner;

@Configuration
@Import({AopConfig.class, CommandsConfig.class, I18nConfig.class})
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public BookService bookService(){
        return new BookService();
    }

    @Bean
    public BookController bookController(Map<Integer, Command> commands, Scanner scanner, Messages messages, LocaleHolder localeHolder){
        return new BookController(commands, scanner, messages, localeHolder);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}