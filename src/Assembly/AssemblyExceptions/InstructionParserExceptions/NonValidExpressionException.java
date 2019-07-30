package Assembly.AssemblyExceptions.InstructionParserExceptions;

public class NonValidExpressionException extends ParserException {
    public NonValidExpressionException(String message) {
        super(message);
    }

    public NonValidExpressionException() {
        super();
    }
}
