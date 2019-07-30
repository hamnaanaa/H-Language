package Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions;

public class WrongSectionException extends RuntimeException {
    public WrongSectionException(String message) {
        super(message);
    }

    public WrongSectionException() {
        super();
    }
}
