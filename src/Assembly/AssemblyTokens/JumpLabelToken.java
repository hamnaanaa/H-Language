package Assembly.AssemblyTokens;

import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidJumpLabelException;
import Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions.WrongJumpLabelException;
import Assembly.AssemblyFunctionality.AssemblyFunctions;
import Assembly.InstructionParser;

/**
 * Class that represents a label token in H-Language assembly instructions
 *
 * @format LABEL_SEPARATOR_OPEN name token LABEL_SEPARATOR_CLOSE
 * @see AssemblyConstants for the valid access separators
 * @see AssemblyFunctions for the implementation details
 * @see InstructionParser
 * @see // TODO link to all instruction classes that use a label token
 */
public class JumpLabelToken extends Token<NameLiteralToken> {
    public NameLiteralToken getJumpLabel() {
        return value;
    }

    /**
     * Constructor for a jump label token
     * Checks whether the given jump label is valid
     *
     * @param jumpLabel jump label to check
     * @throws NonValidJumpLabelException if non-valid jump label found
     * @see AssemblyFunctions for the implementation details
     * @see AssemblyConstants for the valid jump label separators
     */
    public JumpLabelToken(String jumpLabel) throws NonValidJumpLabelException {
        try {
            this.value = handleJumpLabel(jumpLabel);
        } catch (WrongJumpLabelException e) {
            throw new NonValidJumpLabelException("\nNon-valid jump label found: '" + jumpLabel + "'"
                    + e.getMessage());
        }
    }

    /**
     * Method to determine whether the given jump label is valid
     *
     * @param jumpLabel jump label to check
     * @return name literal token if the given jump label is valid
     * @throws WrongJumpLabelException if non-valid jump label found
     */
    private NameLiteralToken handleJumpLabel(String jumpLabel) throws WrongJumpLabelException {
        if (jumpLabel == null || jumpLabel.equals(""))
            throw new WrongJumpLabelException("\nNull-pointer label found");

        if (AssemblyFunctions.isJumpLabel(jumpLabel))
            return new NameLiteralToken(jumpLabel.substring(1, jumpLabel.length() - 1));

        throw new WrongJumpLabelException("\n'" + jumpLabel.substring(1, jumpLabel.length() - 1)
                + "' is not a valid jump label");
    }
}
