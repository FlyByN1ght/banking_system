package exception;

public class EmptyDirectoryException extends RuntimeException {

    public EmptyDirectoryException(String message) {
        super(message);
    }
}