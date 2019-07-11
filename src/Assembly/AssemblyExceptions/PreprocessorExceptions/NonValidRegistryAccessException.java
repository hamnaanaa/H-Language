package Assembly.AssemblyExceptions.PreprocessorExceptions;

public class NonValidRegistryAccessException extends RuntimeException {
    public NonValidRegistryAccessException(String message) {
        super(message);
    }

    public NonValidRegistryAccessException() {
        super();
    }
}
