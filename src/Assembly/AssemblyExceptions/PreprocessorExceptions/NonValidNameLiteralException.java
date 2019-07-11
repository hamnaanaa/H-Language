package Assembly.AssemblyExceptions.PreprocessorExceptions;

public class NonValidNameLiteralException extends RuntimeException {
    public NonValidNameLiteralException(String message) {
        super(message);
    }

    public NonValidNameLiteralException() {
        super();
    }
}
