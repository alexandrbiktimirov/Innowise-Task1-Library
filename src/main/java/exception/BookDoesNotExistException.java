package exception;

public class BookDoesNotExistException extends RuntimeException {
    public BookDoesNotExistException(Long id) {
        super("Book with id %s does not exist".formatted(id));
    }
}
