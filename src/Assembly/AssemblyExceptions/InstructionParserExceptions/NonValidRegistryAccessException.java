package Assembly.AssemblyExceptions.InstructionParserExceptions;

public class NonValidRegistryAccessException extends ParserException {
    public NonValidRegistryAccessException(String message) {
        super(message);
    }

    public NonValidRegistryAccessException() {
        super();
    }
}
