package Assembly.AssemblyExceptions.InstructionParserExceptions;

public class NonValidNameLiteralException extends ParserException {
    public NonValidNameLiteralException(String message) {
        super(message);
    }

    public NonValidNameLiteralException() {
        super();
    }
}
