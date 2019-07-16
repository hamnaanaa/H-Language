package Assembly.AssemblyExceptions.PreprocessorExceptions;

public class NonValidArrayException extends RuntimeException {
    public NonValidArrayException(String message) {
        super(message);
    }

    public NonValidArrayException() {
        super();
    }
}
