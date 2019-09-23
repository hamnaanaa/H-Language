package Assembly.AssemblyExceptions.InstructionParserExceptions;

public class NonValidOperatorException extends ParserException {
    public NonValidOperatorException(String message) {
        super(message);
    }

    public NonValidOperatorException() {
        super();
    }
}
