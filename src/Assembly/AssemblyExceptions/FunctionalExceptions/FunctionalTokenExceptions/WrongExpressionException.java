package Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions;

import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidExpressionException;

public class WrongExpressionException extends NonValidExpressionException {
    public WrongExpressionException(String message) {
        super(message);
    }

    public WrongExpressionException() {
        super();
    }
}
