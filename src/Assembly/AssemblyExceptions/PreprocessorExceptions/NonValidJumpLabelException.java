package Assembly.AssemblyExceptions.PreprocessorExceptions;

public class NonValidJumpLabelException extends PreproccessorException {
    public NonValidJumpLabelException(String message) {
        super(message);
    }

    public NonValidJumpLabelException() {
        super();
    }
}
