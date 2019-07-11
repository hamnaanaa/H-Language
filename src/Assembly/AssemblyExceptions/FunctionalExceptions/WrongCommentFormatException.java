package Assembly.AssemblyExceptions.FunctionalExceptions;

public class WrongCommentFormatException extends RuntimeException {
    public WrongCommentFormatException(String message) {
        super(message);
    }

    public WrongCommentFormatException() {
        super();
    }
}
