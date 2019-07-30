package Assembly.AssemblyExceptions.FunctionalExceptions;

public class NonValidAssemblyInstructionException extends RuntimeException {
    public NonValidAssemblyInstructionException(String message) {
        super(message);
    }

    public NonValidAssemblyInstructionException() {
        super();
    }
}
