package Assembly.AssemblyExceptions.InstructionParserExceptions;

public class NonValidJumpLabelException extends ParserException {
    public NonValidJumpLabelException(String message) {
        super(message);
    }

    public NonValidJumpLabelException() {
        super();
    }
}
