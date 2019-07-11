package Assembly.AssemblyExceptions.PreprocessorExceptions;

public class NonValidStringLiteralException extends RuntimeException {
    public NonValidStringLiteralException(String message) {
        super(message);
    }

    public NonValidStringLiteralException() {
        super();
    }
}
