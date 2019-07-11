package Assembly.AssemblyExceptions.FunctionalExceptions;

public class WrongSectionException extends RuntimeException {
    public WrongSectionException(String message) {
        super(message);
    }

    public WrongSectionException() {
        super();
    }
}
