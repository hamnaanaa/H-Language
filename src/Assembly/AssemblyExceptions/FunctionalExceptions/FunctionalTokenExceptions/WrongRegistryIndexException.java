package Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions;

import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidRegistryAccessException;

public class WrongRegistryIndexException extends NonValidRegistryAccessException {
    public WrongRegistryIndexException(String message) {
        super(message);
    }

    public WrongRegistryIndexException() {
        super();
    }
}
