package command;

public class DisplayAllBooks implements Command {
    private final CommandsContext commandsContext;

    public DisplayAllBooks(CommandsContext commandsContext) {
        this.commandsContext = commandsContext;
    }

    @Override
    public void execute() {
        commandsContext.bookService().readAllBooks().forEach(System.out::println);
    }
}