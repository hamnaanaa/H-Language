package Assembly.AssemblyTokens;

import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions.WrongStringLiteralException;
import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidStringLiteralException;
import Assembly.AssemblyFunctionality.AssemblyFunctions;
import Assembly.InstructionParser;

/**
 * Class that represents a string literal token in H-Language assembly instructions
 *
 * @format [CHAR/STRING]_SEPARATOR string literal [CHAR/STRING]_SEPARATOR
 * @see AssemblyConstants for the valid string and char separators
 * @see AssemblyFunctions for the implementation details
 * @see InstructionParser
 * @see // TODO link to all instruction classes that use a string literal token
 */
public class StringLiteralToken extends Token<String> {
    public String getString() {
        return value;
    }

    /**
     * Constructor for a string literal token
     * Checks whether the given string literal is valid
     *
     * @param stringLiteral string literal to check
     * @throws NonValidStringLiteralException if the given string literal is not valid
     * @see AssemblyFunctions for the implementation details
     * @see AssemblyConstants for the valid string and char separator
     */
    public StringLiteralToken(String stringLiteral) throws NonValidStringLiteralException {
        try {
            this.value = handleStringLiteral(stringLiteral);
        } catch (WrongStringLiteralException e) {
            throw new NonValidStringLiteralException("\nNon-valid string literal found: '" + stringLiteral + "'"
                    + e.getMessage());
        }
    }

    /**
     * Method to determine whether the given string literal is valid
     *
     * @param stringLiteral string literal to check
     * @return string if valid string literal given
     * @throws WrongStringLiteralException if non-valid string literal found
     * @see AssemblyFunctions for the implementation details
     * @see AssemblyConstants for the valid string and char separators
     */
    private String handleStringLiteral(String stringLiteral) throws WrongStringLiteralException {
        if (stringLiteral == null || stringLiteral.equals(""))
            throw new WrongStringLiteralException("\nNull-pointer string found");

        return AssemblyFunctions.formatStringLiteral(stringLiteral);
    }

    @Override
    public String toString() {
        return "\"" + super.toString() + "\"";
    }
}
