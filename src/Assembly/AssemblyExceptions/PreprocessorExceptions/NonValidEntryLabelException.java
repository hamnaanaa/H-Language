package Assembly.AssemblyExceptions.PreprocessorExceptions;

public class NonValidEntryLabelException extends RuntimeException {
    public NonValidEntryLabelException(String message) {
        super(message);
    }

    public NonValidEntryLabelException() {
        super();
    }
}
