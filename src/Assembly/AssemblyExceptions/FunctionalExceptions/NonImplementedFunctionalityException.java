package Assembly.AssemblyExceptions.FunctionalExceptions;

public class NonImplementedFunctionalityException extends RuntimeException {
    public NonImplementedFunctionalityException(String message) {
        super(message);
    }

    public NonImplementedFunctionalityException() {
        super();
    }
}
