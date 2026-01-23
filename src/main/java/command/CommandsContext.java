package command;

import service.BookService;

import java.util.ResourceBundle;
import java.util.Scanner;

public record CommandsContext(Scanner scanner, BookService bookService, ResourceBundle messages) {
}