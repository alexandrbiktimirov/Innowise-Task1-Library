package controller;

import command.Command;
import i18n.Messages;

import java.util.Map;
import java.util.Scanner;

public class AuthorController {
    private final Scanner scanner;
    private final Messages messages;
    private final Map<Integer, Command> authorCommands;

    public AuthorController(Scanner scanner, Messages messages, Map<Integer, Command> authorCommands) {
        this.scanner = scanner;
        this.messages = messages;
        this.authorCommands = authorCommands;
    }

    public void showMenu(){

    }
}
