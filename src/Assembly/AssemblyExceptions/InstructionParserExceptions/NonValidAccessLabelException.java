package Assembly.AssemblyExceptions.InstructionParserExceptions;

public class NonValidAccessLabelException extends ParserException {
    public NonValidAccessLabelException(String message) {
        super(message);
    }

    public NonValidAccessLabelException() {
        super();
    }
}
