package exception;

public class WriteChangesToFileFailureException extends RuntimeException {
    public WriteChangesToFileFailureException(String message) {
        super(message);
    }
}
