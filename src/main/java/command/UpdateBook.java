package command;

import controller.BookController;
import exception.BookDoesNotExistException;

public class UpdateBook implements Command {
    private final CommandsContext commandsContext;

    public UpdateBook(CommandsContext commandsContext) {
        this.commandsContext = commandsContext;
    }

    @Override
    public void execute() {
        commandsContext.bookService().readAllBooks().forEach(System.out::println);

        System.out.println(commandsContext.messages().getString("update.id"));

        String inputId = commandsContext.scanner().nextLine().trim();
        int id = BookController.isFormatValid(inputId);
        if (id == -1 || !doesBookExist(id)) return;

        System.out.println(commandsContext.messages().getString("update.options"));
        System.out.println(commandsContext.messages().getString("update.options.title"));
        System.out.println(commandsContext.messages().getString("update.options.author"));
        System.out.println(commandsContext.messages().getString("update.options.description"));
        System.out.println(commandsContext.messages().getString("update.options.everything"));

        String inputOption = commandsContext.scanner().nextLine().trim();
        int option = BookController.isFormatValid(inputOption);
        if (option == -1) return;

        switch (option) {
            case 1 -> {
                System.out.println(commandsContext.messages().getString("update.title"));
                String newTitle = commandsContext.scanner().nextLine().trim();

                BookController.executeAction(() -> commandsContext.bookService().updateBookTitle(id, newTitle), commandsContext.messages().getString("update.successful.title"));
            }
            case 2 -> {
                System.out.println(commandsContext.messages().getString("update.author"));
                String newAuthor = commandsContext.scanner().nextLine().trim();

                BookController.executeAction(() -> commandsContext.bookService().updateBookAuthor(id, newAuthor), commandsContext.messages().getString("update.successful.author"));
            }
            case 3 -> {
                System.out.println(commandsContext.messages().getString("update.description"));
                String newDescription = commandsContext.scanner().nextLine().trim();

                BookController.executeAction(() -> commandsContext.bookService().updateBookDescription(id, newDescription), commandsContext.messages().getString("update.successful.description"));
            }
            case 4 -> {
                System.out.println(commandsContext.messages().getString("update.title"));
                String newTitle = commandsContext.scanner().nextLine().trim();
                System.out.println(commandsContext.messages().getString("update.author"));
                String newAuthor = commandsContext.scanner().nextLine().trim();
                System.out.println(commandsContext.messages().getString("update.description"));
                String newDescription = commandsContext.scanner().nextLine().trim();

                BookController.executeAction(() -> commandsContext.bookService().updateBook(id, newTitle, newAuthor, newDescription), commandsContext.messages().getString("update.successful.everything"));
            }
            default -> System.out.println(commandsContext.messages().getString("update.invalid.option"));
        }
    }

    private boolean doesBookExist(int id) {
        try {
            commandsContext.bookService().getBookById(id);
            return true;
        } catch (BookDoesNotExistException e) {
            System.out.println(commandsContext.messages().getString("book.exception"));
            return false;
        }
    }
}