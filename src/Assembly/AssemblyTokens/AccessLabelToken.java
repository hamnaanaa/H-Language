package Assembly.AssemblyTokens;

import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidAccessLabelException;
import Assembly.AssemblyExceptions.FunctionalExceptions.WrongAccessLabelException;
import Assembly.AssemblyFunctionality.AssemblyFunctions;
import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.Preprocessor;

/**
 * Class that represents an access label token in H-Language assembly instructions
 *
 * @format ACCESS_SEPARATOR_OPEN name literal ACCESS_SEPARATOR_CLOSE
 * @see AssemblyConstants for the valid access separators
 * @see AssemblyFunctions for the implementation details
 * @see Preprocessor
 * @see // TODO link to all instruction classes that use an access label token
 */
public class AccessLabelToken extends Token<NameLiteralToken> {
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
    private NameLiteralToken handleAccessLabel(String accessLabel) throws WrongAccessLabelException {
        if (accessLabel == null || accessLabel.equals(""))
            throw new WrongAccessLabelException("\nNull-pointer access label found");

        if (AssemblyFunctions.isAccessLabel(accessLabel))
            return new NameLiteralToken(accessLabel.substring(1, accessLabel.length() - 1));

        throw new WrongAccessLabelException("\n'" + accessLabel.substring(1, accessLabel.length() - 1)
                + "' is not a valid access label");
    }
}
