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
        System.out.println("Please enter the id of the book you would like to delete: ");

        String inputId = commandsContext.scanner().nextLine().trim();
        int id = BookController.isFormatValid(inputId);

        BookController.executeAction(() -> commandsContext.bookService().deleteBook(id), "Book deleted successfully");
    }
}
