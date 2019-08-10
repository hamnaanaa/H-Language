package Assembly.AssemblyTokens;

import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions.WrongNameLiteralException;
import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidNameLiteralException;
import Assembly.AssemblyFunctionality.AssemblyFunctions;
import Assembly.AssemblyFunctionality.Pair;

/**
 * Class that represents a name literal with index token in H-Language assembly instructions
 *
 * @format name literal INDEX_SEPARATOR_OPEN index expression INDEX_SEPARATOR_CLOSE
 * @see AssemblyConstants for the valid index separators
 * @see AssemblyFunctions for the implementation details
 */
public class NameLiteralWithIndexToken extends Token<Pair<NameLiteralToken, IntegerExpressionToken>> {

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
}
