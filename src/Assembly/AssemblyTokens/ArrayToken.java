package Assembly.AssemblyTokens;

import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidArrayException;
import Assembly.AssemblyExceptions.FunctionalExceptions.WrongArrayException;
import Assembly.AssemblyFunctionality.AssemblyFunctions;

// TODO : javadoc
public class ArrayToken extends Token<Token<?>[]> {
    // TODO : implementation
    public ArrayToken(String array) throws NonValidArrayException {
        try {
            this.value = handleArray(array);
        } catch (WrongArrayException e) {
            throw new NonValidArrayException("\nNon-valid array found: '" + array + "'"
                    + e.getMessage());
        }
    }

    // TODO : implementation
    private Token<?>[] handleArray(String array) throws WrongArrayException {
        if (array == null || array.equals(""))
            throw new WrongArrayException("\nNull-pointer array found");

        return AssemblyFunctions.tokenizeArray(array);
    }
}
