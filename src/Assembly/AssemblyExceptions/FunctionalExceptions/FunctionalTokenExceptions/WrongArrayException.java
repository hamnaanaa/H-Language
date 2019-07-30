package Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions;

import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidArrayException;

public class WrongArrayException extends NonValidArrayException {
    public WrongArrayException(String message) {
        super(message);
    }

    public WrongArrayException() {
        super();
    }
}
