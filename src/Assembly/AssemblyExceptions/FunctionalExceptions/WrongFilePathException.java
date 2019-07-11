package Assembly.AssemblyExceptions.FunctionalExceptions;

public class WrongFilePathException extends RuntimeException {
    public WrongFilePathException(String message) {
        super(message);
    }

    public WrongFilePathException() {
        super();
    }
}
