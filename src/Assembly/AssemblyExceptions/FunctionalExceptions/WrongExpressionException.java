package Assembly.AssemblyExceptions.FunctionalExceptions;

import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidExpressionException;

public class WrongExpressionException extends NonValidExpressionException {
    public WrongExpressionException(String message) {
        super(message);
    }

    public WrongExpressionException() {
        super();
    }
}
