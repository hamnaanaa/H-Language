package Assembly.AssemblyExceptions.FunctionalExceptions;

import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidRegistryAccessException;

public class WrongRegistryIndexException extends NonValidRegistryAccessException {
    public WrongRegistryIndexException(String message) {
        super(message);
    }

    public WrongRegistryIndexException() {
        super();
    }
}
