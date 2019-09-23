package Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions;

import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidOperatorException;

public class WrongOperatorNameException extends NonValidOperatorException {
    public WrongOperatorNameException(String message) {
        super(message);
    }

    public WrongOperatorNameException() {
        super();
    }
}
