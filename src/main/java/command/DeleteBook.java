package command;

import controller.BookController;

public class DeleteBook implements Command {
    private final CommandsContext commandsContext;

    public DeleteBook(CommandsContext commandsContext) {
        this.commandsContext = commandsContext;
    }

    @Override
    public void execute() {
        commandsContext.bookService().readAllBooks().forEach(System.out::println);
        System.out.println(commandsContext.messages().getString("delete.id"));

        String inputId = commandsContext.scanner().nextLine().trim();
        int id = BookController.isFormatValid(inputId);

        BookController.executeAction(() -> commandsContext.bookService().deleteBook(id), commandsContext.messages().getString("delete.successful"));
    }
}
