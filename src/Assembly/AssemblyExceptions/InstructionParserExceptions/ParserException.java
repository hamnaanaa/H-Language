package Assembly.AssemblyExceptions.InstructionParserExceptions;

public class ParserException extends RuntimeException {
    public ParserException(String message) {
        super(message);
    }

    public ParserException() {
        super();
    }
}
