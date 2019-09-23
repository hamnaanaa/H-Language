package Assembly.AssemblyTokens;

import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions.WrongNameLiteralException;
import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidNameLiteralException;
import Assembly.AssemblyFunctionality.AssemblyFunctions;
import Assembly.AssemblyFunctionality.Pair;
import Assembly.InstructionParser;

/**
 * Class that represents a name literal with index token in H-Language assembly instructions
 *
 * @format name literal INDEX_SEPARATOR_OPEN index expression INDEX_SEPARATOR_CLOSE
 * @see AssemblyConstants for the valid index separators
 * @see AssemblyFunctions for the implementation details
 */
public class NameLiteralWithIndexToken extends Token<Pair<NameLiteralWithIndexToken.NameLiteralToken, IntegerExpressionToken>> {

    /**
     * Constructor for a name literal with index token
     * Checks whether the given name literal and index are valid
     *
     * @param nameLiteral name literal with index to check
     * @throws NonValidNameLiteralException if the given name literal or index are not valid
     * @see AssemblyFunctions for the implementation details
     * @see AssemblyConstants for the valid index separators
     */
    public NameLiteralWithIndexToken(String nameLiteral) throws NonValidNameLiteralException {
        try {
            this.value = handleNameLiteralWithIndex(nameLiteral);
        } catch (WrongNameLiteralException e) {
            throw new NonValidNameLiteralException("\nNon-valid name literal with index found: '" + nameLiteral
                    + "'" + e.getMessage());
        }
    }

    /**
     * Method to determine whether the given name literal with index is valid
     *
     * @param nameLiteral name literal with index to extract name literal token and integer expression token from
     * @return pair of name literal token and integer expression token
     * @throws WrongNameLiteralException if non-valid name literal with index found
     * @see NameLiteralToken for the vaild name literal token
     * @see IntegerExpressionToken for the valid integer expression token
     * @see AssemblyConstants for the valid separators
     */
    private Pair<NameLiteralToken, IntegerExpressionToken> handleNameLiteralWithIndex(String nameLiteral)
            throws WrongNameLiteralException {
        if (nameLiteral == null || nameLiteral.equals(""))
            throw new WrongNameLiteralException("\nNull-pointer name literal found");

        Pair<NameLiteralToken, IntegerExpressionToken> pair =
                AssemblyFunctions.extractNameLiteralWithIndexTokens(nameLiteral);
        if (pair.getValue().value.compareTo(0) < 0)
            throw new WrongNameLiteralException("\nNegative indices are not allowed for name literals with indices\n" +
                    "Index found: '" + pair.getValue().getExpressionValue() + "'");

        return pair;
    }

    /**
     * Class that represents a name literal token in H-Language assembly instructions
     *
     * @format nameLiteral
     * @see AssemblyFunctions for the implementation details
     * @see AssemblyConstants for the valid alphabets to use
     * @see InstructionParser
     * @see // TODO link to the variableAssignment/Label classes (and all other instruction classes that use nameLiteral
     */
    public static class NameLiteralToken extends Token<String> implements Arrayable {

        public String getNameLiteral() {
            return value;
        }

        /**
         * Constructor for a name literal token
         * Checks whether the given name literal is valid
         *
         * @param nameLiteral name literal to check
         * @throws NonValidNameLiteralException if the given name literal is not valid
         * @see AssemblyFunctions for the implementation details
         * @see AssemblyConstants for the valid alphabets to use
         */
        public NameLiteralToken(String nameLiteral) throws NonValidNameLiteralException {
            try {
                this.value = handleNameLiteral(nameLiteral);
            } catch (WrongNameLiteralException e) {
                throw new NonValidNameLiteralException("\nNon-valid name literal found: '" + nameLiteral + "'"
                        + e.getMessage());
            }
        }

        /**
         * Method to determine whether the given name literal is valid
         *
         * @param nameLiteral name literal to check
         * @return name literal if valid
         * @throws WrongNameLiteralException if non-valid name literal found
         * @see AssemblyFunctions for the implementation details
         * @see AssemblyConstants for the valid alphabets to use
         */
        private String handleNameLiteral(String nameLiteral) throws WrongNameLiteralException {
            if (nameLiteral == null || nameLiteral.equals(""))
                throw new WrongNameLiteralException("\nNull-pointer name literal found");

            if (AssemblyFunctions.isNameLiteral(nameLiteral))
                return nameLiteral;

            throw new WrongNameLiteralException("\n'" + nameLiteral + "' is not allowed as a name literal");
        }
    }
}
