package Assembly.AssemblyTokens;

import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidOperatorException;
import Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions.WrongOperatorNameException;
import Assembly.AssemblyFunctionality.AssemblyFunctions;
import Assembly.AssemblyConstants.Operators;
import Assembly.InstructionParser;
import Assembly.AssemblyInstructions.TEXT_Instruction;

/**
 * Class that represents an operator token in H-Language assembly instructions
 *
 * @format operator (reserved word)
 * @see AssemblyFunctions for the implementation details
 * @see Operators for the list of valid operators
 * @see TEXT_Instruction for all instructions that use operator token
 * @see InstructionParser
 */
public class OperatorToken extends Token<Operators> {
    public Operators getOP() {
        return value;
    }

    /**
     * Constructor for an operator token
     * Checks whether the given operator name is valid
     *
     * @param OP operator name to check
     * @throws NonValidOperatorException if the given operator is not valid
     * @see Operators for the list of valid operators
     * @see AssemblyFunctions for the implementation details
     */
    public OperatorToken(String OP) throws NonValidOperatorException {
        try {
            this.value = handleOperator(OP);
        } catch (WrongOperatorNameException e) {
            throw new NonValidOperatorException("\nNon-valid operator found: '" + OP + "'" + e.getMessage());
        }
    }

    /**
     * Method to determine whether the given operator name is a valid operator
     *
     * @param OP operator name to check
     * @return operator name if valid
     * @throws WrongOperatorNameException if non-valid operator name found
     * @see Operators for the list of valid operators
     * @see AssemblyFunctions for the implementation details
     */
    private Operators handleOperator(String OP) throws WrongOperatorNameException {
        if (OP == null || OP.equals(""))
            throw new WrongOperatorNameException("\nNull-Pointer operator found");

        return (AssemblyFunctions.getOperator(OP));
    }
}
