package Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions;

import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidRegistryAccessException;

public class WrongRegistryNameException extends NonValidRegistryAccessException {
    public WrongRegistryNameException(String message) {
        super(message);
    }

    public WrongRegistryNameException() {
        super();
    }
}
