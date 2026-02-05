package controller;

import command.Command;
import i18n.Messages;

import java.util.Map;
import java.util.Scanner;

public class GenreController {
    private final Scanner scanner;
    private final Messages messages;
    private final Map<Integer, Command> genreCommands;

    public GenreController(Scanner scanner, Messages messages, Map<Integer, Command> genreCommands) {
        this.scanner = scanner;
        this.messages = messages;
        this.genreCommands = genreCommands;
    }

    public void showMenu(){

    }
}