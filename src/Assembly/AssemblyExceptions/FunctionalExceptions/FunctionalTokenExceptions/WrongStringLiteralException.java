package Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions;

import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidStringLiteralException;

public class WrongStringLiteralException extends NonValidStringLiteralException {
    public WrongStringLiteralException(String message) {
        super(message);
    }

    public WrongStringLiteralException() {
        super();
    }
}
