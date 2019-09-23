package Assembly.AssemblyExceptions.FunctionalExceptions;

import java.io.IOException;

public class WrongFilePathException extends IOException {
    public WrongFilePathException(String message) {
        super(message);
    }

    public WrongFilePathException() {
        super();
    }
}
