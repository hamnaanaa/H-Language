package Assembly.AssemblyExceptions.FunctionalExceptions;

public class WrongAssemblyLineException extends RuntimeException {
    public WrongAssemblyLineException(String message) {
        super(message);
    }

    public WrongAssemblyLineException() {
        super();
    }
}
