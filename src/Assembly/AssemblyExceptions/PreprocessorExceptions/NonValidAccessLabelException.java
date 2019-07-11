package Assembly.AssemblyExceptions.PreprocessorExceptions;

public class NonValidAccessLabelException extends RuntimeException {
    public NonValidAccessLabelException(String message) {
        super(message);
    }

    public NonValidAccessLabelException() {
        super();
    }
}
