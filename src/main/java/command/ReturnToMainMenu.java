package command;

import controller.AppController;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ReturnToMainMenu {
    private final AppController appController;

    public ReturnToMainMenu(@Lazy AppController appController) {
        this.appController = appController;
    }

    public void execute() {
        appController.showMenu();
    }
}