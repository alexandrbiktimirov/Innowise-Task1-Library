package command;

public class CreateNewBook implements Command {
    private final CommandsContext commandsContext;

    public CreateNewBook(CommandsContext commandsContext) {
        this.commandsContext = commandsContext;
    }

    @Override
    public void execute() {
        while (true) {
            System.out.println("Please enter the name of the book: ");
            String name = commandsContext.scanner().nextLine().trim();

            if (isEmpty(name, "Invalid name of the book, please try again")) continue;

            System.out.println("Please enter the author of the book: ");
            String author = commandsContext.scanner().nextLine().trim();

            if (isEmpty(author, "Invalid author of the book, please try again")) continue;

            System.out.println("Please enter the description the book: ");
            String description = commandsContext.scanner().nextLine().trim();

            if (isEmpty(description, "Invalid description of the book, please try again")) continue;

            commandsContext.bookService().createBook(name, author, description);
            break;
        }
    }

    private boolean isEmpty(String something, String message) {
        if (something.isEmpty()) {
            System.out.println(message);
            return true;
        }
        return false;
    }
}
