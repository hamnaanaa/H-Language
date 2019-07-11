package Assembly.AssemblyExceptions.FunctionalExceptions;

import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidEntryLabelException;

public class WrongEntryLabelException extends NonValidEntryLabelException {
    public WrongEntryLabelException(String message) {
        super(message);
    }

    public WrongEntryLabelException() {
        super();
    }
}
