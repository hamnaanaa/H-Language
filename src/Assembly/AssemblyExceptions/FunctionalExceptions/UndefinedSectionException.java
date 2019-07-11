package Assembly.AssemblyExceptions.FunctionalExceptions;

public class UndefinedSectionException extends RuntimeException {
    public UndefinedSectionException(String message) {
        super(message);
    }

    public UndefinedSectionException() {
        super();
    }
}
