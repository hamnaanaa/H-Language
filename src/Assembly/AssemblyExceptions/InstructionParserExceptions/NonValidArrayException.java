package Assembly.AssemblyExceptions.InstructionParserExceptions;

public class NonValidArrayException extends ParserException {
    public NonValidArrayException(String message) {
        super(message);
    }

    public NonValidArrayException() {
        super();
    }
}
