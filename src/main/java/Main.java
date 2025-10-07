import config.Config;
import controller.AppController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(Config.class)) {
            var appController = context.getBean(AppController.class);

            appController.start();
        }
    }
}
