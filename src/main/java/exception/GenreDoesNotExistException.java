package exception;

public class GenreDoesNotExistException extends RuntimeException {
    public GenreDoesNotExistException(Long id) {
        super("Genre with id %s does not exist".formatted(id));
    }
}
