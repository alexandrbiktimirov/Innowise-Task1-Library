package command;

import exception.BookDoesNotExistException;
import i18n.Messages;
import org.springframework.stereotype.Component;

@Component
public class CommandExecutor {
    private final Messages messages;

    public CommandExecutor(Messages messages) {
        this.messages = messages;
    }

    public void executeAction(Runnable action, String message) {
        try {
            action.run();
            System.out.println(message);
        } catch (BookDoesNotExistException e) {
            System.out.println(messages.get("book.exception"));
        }
    }
}