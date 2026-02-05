package exception;

public class LoadFromFileFailureException extends RuntimeException {
    public LoadFromFileFailureException(String message) {
        super(message);
    }
}
