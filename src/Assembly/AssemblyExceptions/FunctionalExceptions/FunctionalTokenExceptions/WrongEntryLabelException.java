package Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions;

import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidEntryLabelException;

public class WrongEntryLabelException extends NonValidEntryLabelException {
    public WrongEntryLabelException(String message) {
        super(message);
    }

    public WrongEntryLabelException() {
        super();
    }
}
