package Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions;

import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidNameLiteralException;

public class WrongNameLiteralException extends NonValidNameLiteralException {
    public WrongNameLiteralException(String message) {
        super(message);
    }

    public WrongNameLiteralException() {
        super();
    }
}
