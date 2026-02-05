package command;

import i18n.Messages;
import org.springframework.stereotype.Component;

@Component
public class QuitProgram implements Command {
    private final Messages messages;

    public QuitProgram(Messages messages) {
        this.messages = messages;
    }

    @Override
    public void execute() {
        System.out.println(messages.get("app.quitting"));
        System.exit(0);
    }
}