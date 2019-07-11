package Assembly.AssemblyExceptions.FunctionalExceptions;

import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidAccessLabelException;

public class WrongAccessLabelException extends NonValidAccessLabelException {
    public WrongAccessLabelException(String message) {
        super(message);
    }

    public WrongAccessLabelException() {
        super();
    }
}
