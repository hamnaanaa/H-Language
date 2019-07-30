package Assembly.AssemblyTokens;

import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions.WrongEntryLabelException;
import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidEntryLabelException;
import Assembly.AssemblyFunctionality.AssemblyFunctions;
import Assembly.InstructionParser;

/**
 * Class that represents an entry label token in H-Language assembly instructions
 *
 * @format ENTRY_SEPARATOR OPEN SPACE name literal ENTRY_SEPARATOR_CLOSE
 * @see AssemblyConstants for the valid entry label separators
 * @see AssemblyFunctions for the implementation details
 * @see InstructionParser
 * @see // TODO link to all instruction classes that use an entry label token
 */
public class EntryLabelToken extends Token<NameLiteralToken> {
    public NameLiteralToken getEntryLabel() {
        return value;
    }

    /**
     * Constructor for an entry label token
     * Checks whether the given entry label is valid
     *
     * @param entryLabel entry label to check
     * @throws NonValidEntryLabelException if the given entry label is not valid
     * @see AssemblyFunctions for the implementation details
     * @see AssemblyConstants for the valid entry label separators
     */
    public EntryLabelToken(String entryLabel) throws NonValidEntryLabelException {
        try {
            this.value = handleEntryLabel(entryLabel);
        } catch (WrongEntryLabelException e) {
            throw new NonValidEntryLabelException("\nNon-valid entry label found: '" + entryLabel + "'"
                    + e.getMessage());
        }
    }

    /**
     * Method to determine whether the given entry label is valid
     *
     * @param entryLabel entry label to check
     * @return name literal token if valid entry label given
     * @throws WrongEntryLabelException if non-valid entry label found
     * @see AssemblyFunctions for the implementation details
     * @see AssemblyConstants for the valid entry label separators
     */
    private NameLiteralToken handleEntryLabel(String entryLabel) throws WrongEntryLabelException {
        if (entryLabel == null || entryLabel.equals(""))
            throw new WrongEntryLabelException("\nNull-pointer entry label found");

        if (AssemblyFunctions.isEntryLabel(entryLabel))
            return new NameLiteralToken(entryLabel.substring(3, entryLabel.length() - 1));

        throw new WrongEntryLabelException("\n'" + entryLabel.substring(3, entryLabel.length() - 1)
                + "' is not a valid entry label");
    }
}
