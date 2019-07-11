package Assembly.AssemblyExceptions.FunctionalExceptions;

import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidOperatorException;

public class WrongOperatorNameException extends NonValidOperatorException {
    public WrongOperatorNameException(String message) {
        super(message);
    }

    public WrongOperatorNameException() {
        super();
    }
}
