package Assembly.AssemblyExceptions.PreprocessorExceptions;

public class NonValidOperatorException extends RuntimeException {
    public NonValidOperatorException(String message) {
        super(message);
    }

    public NonValidOperatorException() {
        super();
    }
}
