import config.Config;
import controller.BookController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(Config.class)) {
            var bookController = context.getBean(BookController.class);

            bookController.start();
        }
    }
}