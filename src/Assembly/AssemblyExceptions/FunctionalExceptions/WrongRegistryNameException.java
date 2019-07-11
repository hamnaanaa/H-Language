package Assembly.AssemblyExceptions.FunctionalExceptions;

import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidRegistryAccessException;

public class WrongRegistryNameException extends NonValidRegistryAccessException {
    public WrongRegistryNameException(String message) {
        super(message);
    }

    public WrongRegistryNameException() {
        super();
    }
}
