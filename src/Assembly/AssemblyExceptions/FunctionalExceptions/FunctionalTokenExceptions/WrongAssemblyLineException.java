package Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions;

public class WrongAssemblyLineException extends RuntimeException {
    public WrongAssemblyLineException(String message) {
        super(message);
    }

    public WrongAssemblyLineException() {
        super();
    }
}
