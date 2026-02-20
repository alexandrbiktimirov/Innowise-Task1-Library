package exception;

public class GenreDoesNotExistException extends RuntimeException {
    public GenreDoesNotExistException(String message) {
        super(message);
    }
}
