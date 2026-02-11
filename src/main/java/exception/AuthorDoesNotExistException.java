package exception;

public class AuthorDoesNotExistException extends RuntimeException {
    public AuthorDoesNotExistException(Long id) {
        super("Author with id %s does not exist".formatted(id));
    }
}
