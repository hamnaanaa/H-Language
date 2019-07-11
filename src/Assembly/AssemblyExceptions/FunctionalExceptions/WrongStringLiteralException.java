package Assembly.AssemblyExceptions.FunctionalExceptions;

import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidStringLiteralException;

public class WrongStringLiteralException extends NonValidStringLiteralException {
    public WrongStringLiteralException(String message) {
        super(message);
    }

    public WrongStringLiteralException() {
        super();
    }
}
