package command;

import controller.AppController;
import org.springframework.stereotype.Component;

@Component
public class ReturnToMainMenu implements Command {
    private final AppController appController;

    public ReturnToMainMenu(AppController appController) {
        this.appController = appController;
    }

    @Override
    public void execute() {
        appController.showMenu();
    }
}