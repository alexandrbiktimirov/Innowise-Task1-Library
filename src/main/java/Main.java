import controller.AppController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            AppController appController = context.getBean(AppController.class);

            appController.chooseLanguage();
        }
    }
}