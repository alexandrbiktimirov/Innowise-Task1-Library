package command;

public class QuitProgram implements Command {
    private final CommandsContext commandsContext;

    public QuitProgram(CommandsContext commandsContext) {
        this.commandsContext = commandsContext;
    }

    @Override
    public void execute() {
        System.out.println(commandsContext.messages().getString("quitting"));
        commandsContext.bookService().writeChangesToFile();
        System.exit(0);
    }
}
