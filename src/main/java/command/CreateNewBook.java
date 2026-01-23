package command;

public class CreateNewBook implements Command {
    private final CommandsContext commandsContext;

    public CreateNewBook(CommandsContext commandsContext) {
        this.commandsContext = commandsContext;
    }

    @Override
    public void execute() {
        while (true) {
            System.out.println(commandsContext.messages().getString("create.name"));
            String name = commandsContext.scanner().nextLine().trim();

            if (isEmpty(name, commandsContext.messages().getString("create.invalid.name"))) continue;

            System.out.println(commandsContext.messages().getString("create.author"));
            String author = commandsContext.scanner().nextLine().trim();

            if (isEmpty(author, commandsContext.messages().getString("create.invalid.author"))) continue;

            System.out.println(commandsContext.messages().getString("create.description"));
            String description = commandsContext.scanner().nextLine().trim();

            if (isEmpty(description, commandsContext.messages().getString("create.invalid.description"))) continue;

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
