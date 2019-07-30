package Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions;

import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidJumpLabelException;

public class WrongJumpLabelException extends NonValidJumpLabelException {
    public WrongJumpLabelException(String message) {
        super(message);
    }

    public WrongJumpLabelException() {
        super();
    }
}
