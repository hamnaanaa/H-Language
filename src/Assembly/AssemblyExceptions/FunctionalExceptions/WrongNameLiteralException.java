package Assembly.AssemblyExceptions.FunctionalExceptions;

import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidNameLiteralException;

public class WrongNameLiteralException extends NonValidNameLiteralException {
    public WrongNameLiteralException(String message) {
        super(message);
    }

    public WrongNameLiteralException() {
        super();
    }
}
