package Assembly.AssemblyExceptions.PreprocessorExceptions;

public class NonValidExpressionException extends RuntimeException {
    public NonValidExpressionException(String message) {
        super(message);
    }

    public NonValidExpressionException() {
        super();
    }
}
