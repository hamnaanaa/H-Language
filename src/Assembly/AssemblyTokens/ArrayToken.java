package Assembly.AssemblyTokens;

import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions.WrongArrayException;
import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidArrayException;
import Assembly.AssemblyFunctionality.AssemblyFunctions;
import Assembly.InstructionParser;

/**
 * Class that represents an array token in H-Language assembly instructions
 *
 * @format ARRAY_SEPARATOR_OPEN (arrayValue OPERATOR_SEPARATOR)* ARRAY_SEPARATOR_CLOSE
 * @see AssemblyConstants for the valid array and operator separators
 * @see AssemblyFunctions for the implementatiton details
 * @see InstructionParser
 * @see // TODO link to all instruction classes that use an array token
 */
public class ArrayToken extends Token<Token<?>[]> implements Arrayable {

    /**
     * Constructor for an array token
     * Checks whether the given array is valid
     * Deep arrays are defined as well
     *
     * @param array array token to check
     * @throws NonValidArrayException if the given array is not valid
     * @see AssemblyFunctions for the implementation details
     * @see AssemblyConstants for the valid array and operator separators
     */
    public ArrayToken(String array) throws NonValidArrayException {
        try {
            this.value = handleArray(array);
        } catch (WrongArrayException e) {
            throw new NonValidArrayException("\nNon-valid array found: '" + array + "'"
                    + e.getMessage());
        }
    }

    /**
     * Method to determine whether the given array is valid (deep arrays are supported)
     *
     * @param array array to check
     * @return array of tokens if valid array given
     * @throws WrongArrayException if non-valid array found
     * @see AssemblyFunctions for the implementation details
     * @see AssemblyConstants for the valid array and operator separators
     */
    private Token<?>[] handleArray(String array) throws WrongArrayException {
        if (array == null || array.equals(""))
            throw new WrongArrayException("\nNull-pointer array found");

        return AssemblyFunctions.tokenizeArray(array);
    }

    /**
     * Method to output the given array as string. Supports deep arrays
     * Calls recursively toString on deep arrays as well
     *
     * @return string representation of the array
     */
    @Override
    public String toString() {
        StringBuilder outputBuilder = new StringBuilder(String.valueOf(AssemblyConstants.ARRAY_SEPARATOR_OPEN) + ' ');
        for (Token<?> token : value)
            outputBuilder.append(token.toString()).append(AssemblyConstants.OPERATOR_SEPARATOR).append(' ');

        if (outputBuilder.length() > 2)
            outputBuilder.setLength(outputBuilder.length() - 2);
        return outputBuilder.append(' ').append(AssemblyConstants.ARRAY_SEPARATOR_CLOSE).toString();
    }
}
