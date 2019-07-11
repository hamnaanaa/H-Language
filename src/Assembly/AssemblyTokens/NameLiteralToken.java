package Assembly.AssemblyTokens;

import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidNameLiteralException;
import Assembly.AssemblyExceptions.FunctionalExceptions.WrongNameLiteralException;
import Assembly.AssemblyFunctionality.AssemblyFunctions;
import Assembly.Preprocessor;

/**
 * Class that represents a name literal token in H-Language assembly instructions
 *
 * @format nameLiteral
 * @see AssemblyFunctions for the implementation details
 * @see AssemblyConstants for the valid alphabets to use
 * @see Preprocessor
 * @see // TODO link to the variableAssignment/Label classes (and all other instruction classes that use nameLiteral
 */
public class NameLiteralToken extends Token<String> {

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
