package Assembly.AssemblyExceptions.PreprocessorExceptions;

public class NonValidJumpLabelException extends RuntimeException {
    public NonValidJumpLabelException(String message) {
        super(message);
    }

    public NonValidJumpLabelException() {
        super();
    }
}
