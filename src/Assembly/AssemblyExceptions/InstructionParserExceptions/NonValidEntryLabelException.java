package Assembly.AssemblyExceptions.InstructionParserExceptions;

public class NonValidEntryLabelException extends ParserException {
    public NonValidEntryLabelException(String message) {
        super(message);
    }

    public NonValidEntryLabelException() {
        super();
    }
}
