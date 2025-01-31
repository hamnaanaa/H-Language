package Assembly.AssemblyTokens;

import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions.WrongAccessLabelException;
import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidAccessLabelException;
import Assembly.AssemblyFunctionality.AssemblyFunctions;
import Assembly.AssemblyFunctionality.Pair;
import Assembly.InstructionParser;

/**
 * Class that represents an access label with index token in H-Language assembly instructions
 *
 * @format ACCESS_SEPARATOR_OPEN name literal ACCESS_SEPARATOR_CLOSE INDEX_SEPARATOR OPEN index expression INDEX_SEPARATOR_CLOSE
 * @see AssemblyConstants for the valid access separators
 * @see AssemblyFunctions for the implementation details
 * @see InstructionParser
 * @see // TODO link to all instruction classes that use an access label token
 */
public class AccessLabelWithIndexToken extends Token<Pair<AccessLabelWithIndexToken.AccessLabelToken, IntegerExpressionToken>> {

    /**
     * Constructor for an access label with index token
     * Checks whether the given access label and index are valid
     *
     * @param accessLabel access label with index to check
     * @throws NonValidAccessLabelException if the given access label or index are not valid
     * @see AssemblyFunctions for the implementation details
     * @see AssemblyConstants for the valid access and index separators
     */
    public AccessLabelWithIndexToken(String accessLabel) throws NonValidAccessLabelException {
        try {
            this.value = handleAccessLabelWithIndex(accessLabel);
        } catch (WrongAccessLabelException e) {
            throw new NonValidAccessLabelException("\nNon-valid access label with index found: '" + accessLabel
                    + "'" + e.getMessage());
        }
    }

    /**
     * Method to determine whether the given access label with index is valid
     *
     * @param accessLabel access label with index to extract access label token and integer expression token from
     * @return pair of access label token and integer expression token
     * @throws WrongAccessLabelException if non-valid access label with index found
     * @see AssemblyFunctions for the implementation details
     * @see AccessLabelToken for the vaild access label token
     * @see IntegerExpressionToken for the valid integer expression token
     * @see AssemblyConstants for the valid separators
     */
    private Pair<AccessLabelToken, IntegerExpressionToken> handleAccessLabelWithIndex(String accessLabel)
            throws WrongAccessLabelException {
        if (accessLabel == null || accessLabel.equals(""))
            throw new WrongAccessLabelException("\nNull-pointer access label found");

        Pair<AccessLabelToken, IntegerExpressionToken> pair =
                AssemblyFunctions.extractAccessLabelWithIndexTokens(accessLabel);
        if (pair.getValue().value.compareTo(0) < 0)
            throw new WrongAccessLabelException("\nNegative indices are not allowed for access labels with indices\n" +
                    "Index found: '" + pair.getValue().getExpressionValue() + "'");

        return pair;
    }

    /**
     * Class that represents an access label token in H-Language assembly instructions
     *
     * @format ACCESS_SEPARATOR_OPEN name literal ACCESS_SEPARATOR_CLOSE
     * @see AssemblyConstants for the valid access separators
     * @see AssemblyFunctions for the implementation details
     * @see InstructionParser
     * @see // TODO link to all instruction classes that use an access label token
     */
    public static class AccessLabelToken extends Token<NameLiteralWithIndexToken.NameLiteralToken> {
        public String getAccessLabel() {
            return value.getNameLiteral();
        }

        /**
         * Constructor for an access label token
         * Checks whether the given access label is valid
         *
         * @param accessLabel access label to check
         * @throws NonValidAccessLabelException if the given access label is not valid
         * @see AssemblyFunctions for the implementation details
         * @see AssemblyConstants for the valid access separators
         */
        public AccessLabelToken(String accessLabel) throws NonValidAccessLabelException {
            try {
                this.value = handleAccessLabel(accessLabel);
            } catch (WrongAccessLabelException e) {
                throw new NonValidAccessLabelException("\nNon-valid access label found: '" + accessLabel + "'"
                        + e.getMessage());
            }
        }

        /**
         * Method to determine whether the given access label is valid
         *
         * @param accessLabel access label to check
         * @return name literal token if valid access label given
         * @throws WrongAccessLabelException if non-valid access label found
         * @see AssemblyFunctions for the implementation details
         * @see AssemblyConstants for the valid access separators
         */
        private NameLiteralWithIndexToken.NameLiteralToken handleAccessLabel(String accessLabel) throws WrongAccessLabelException {
            if (accessLabel == null || accessLabel.equals(""))
                throw new WrongAccessLabelException("\nNull-pointer access label found");

            if (AssemblyFunctions.isAccessLabel(accessLabel))
                return new NameLiteralWithIndexToken.NameLiteralToken(accessLabel.substring(1, accessLabel.length() - 1));

            throw new WrongAccessLabelException("\n'" + accessLabel.substring(1, accessLabel.length() - 1)
                    + "' is not a valid access label");
        }
    }
}
