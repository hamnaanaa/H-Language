package Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions;

import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidAccessLabelException;

public class WrongAccessLabelException extends NonValidAccessLabelException {
    public WrongAccessLabelException(String message) {
        super(message);
    }

    public WrongAccessLabelException() {
        super();
    }
}
