import config.AppConfig;
import controller.BookController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            var bookController = context.getBean(BookController.class);

            bookController.start();
        }
    }
}