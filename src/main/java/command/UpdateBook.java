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

        System.out.println("Please enter the id of the book you would like to edit: ");

        String inputId = commandsContext.scanner().nextLine().trim();
        int id = BookController.isFormatValid(inputId);
        if (id == -1 || !doesBookExist(id)) return;

        System.out.println("What would you like to edit?");
        System.out.println("1 - Title");
        System.out.println("2 - Author");
        System.out.println("3 - Description");
        System.out.println("4 - Everything");

        String inputOption = commandsContext.scanner().nextLine().trim();
        int option = BookController.isFormatValid(inputOption);
        if (option == -1) return;

        switch (option) {
            case 1 -> {
                System.out.println("Enter the new title of the book: ");
                String newTitle = commandsContext.scanner().next().trim();

                BookController.executeAction(() -> commandsContext.bookService().updateBookTitle(id, newTitle), "Title updated successfully");
            }
            case 2 -> {
                System.out.println("Enter the new author of the book: ");
                String newAuthor = commandsContext.scanner().next().trim();

                BookController.executeAction(() -> commandsContext.bookService().updateBookAuthor(id, newAuthor), "Author updated successfully");
            }
            case 3 -> {
                System.out.println("Enter the new description of the book: ");
                String newDescription = commandsContext.scanner().next().trim();

                BookController.executeAction(() -> commandsContext.bookService().updateBookDescription(id, newDescription), "Description updated successfully");
            }
            case 4 -> {
                System.out.println("Enter the new title of the book: ");
                String newTitle = commandsContext.scanner().next().trim();
                System.out.println("Enter the new author of the book: ");
                String newAuthor = commandsContext.scanner().next().trim();
                System.out.println("Enter the new description of the book: ");
                String newDescription = commandsContext.scanner().next().trim();

                BookController.executeAction(() -> commandsContext.bookService().updateBook(id, newTitle, newAuthor, newDescription), "Book updated successfully");
            }
            default -> System.out.println("Invalid option. Please try again");
        }
    }

    private boolean doesBookExist(int id) {
        try {
            commandsContext.bookService().getBookById(id);
            return true;
        } catch (BookDoesNotExistException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}