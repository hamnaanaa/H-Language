package Assembly.AssemblyExceptions.FunctionalExceptions;

import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidJumpLabelException;

public class WrongJumpLabelException extends NonValidJumpLabelException {
    public WrongJumpLabelException(String message) {
        super(message);
    }

    public WrongJumpLabelException() {
        super();
    }
}
