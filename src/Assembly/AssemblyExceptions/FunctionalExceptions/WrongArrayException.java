package Assembly.AssemblyExceptions.FunctionalExceptions;

import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidArrayException;

public class WrongArrayException extends NonValidArrayException {
    public WrongArrayException(String message) {
        super(message);
    }

    public WrongArrayException() {
        super();
    }
}
