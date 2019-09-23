package Assembly.AssemblyExceptions.InstructionParserExceptions;

public class NonValidStringLiteralException extends ParserException {
    public NonValidStringLiteralException(String message) {
        super(message);
    }

    public NonValidStringLiteralException() {
        super();
    }
}
