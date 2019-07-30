package Assembly.AssemblyTokens;

import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.AssemblyExceptions.FunctionalExceptions.NonImplementedFunctionalityException;
import Assembly.AssemblyExceptions.InstructionParserExceptions.NonValidExpressionException;
import Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions.WrongExpressionException;
import Assembly.AssemblyFunctionality.AssemblyFunctions;
import Assembly.InstructionParser;

/**
 * Class that represents an integer expression token in H-Language assembly instructions
 *
 * @format expression
 * @see AssemblyConstants for the valid interval for integer values
 * @see AssemblyFunctions for the implementation details of the expression evaluation
 * @see InstructionParser
 * @see // TODO : link to all instruction classes that use an integer expression
 */
public class IntegerExpressionToken extends ExpressionToken<Integer> {
    public int getExpressionValue() {
        return value;
    }

    /**
     * Constructor for an integer expression
     * Takes string expressions and evaluate them
     *
     * @param expression expression to evaluate
     * @throws NonValidExpressionException if the given string expression cannot be evaluated
     */
    public IntegerExpressionToken(String expression) throws NonValidExpressionException {
        try {
            this.value = handleExpression(expression);
        } catch (WrongExpressionException e) {
            throw new NonValidExpressionException("\nNon-valid expression found: '" + expression + "'"
                    + e.getMessage());
        }
    }

    /**
     * Constructor for an integer expression
     * Takes integer values and checks the limits
     *
     * @param constantValue integer value to check
     * @throws NonValidExpressionException if the given integer cannot be represented with given architecture
     */
    public IntegerExpressionToken(int constantValue) throws NonValidExpressionException {
        try {
            if (isRepresentable(constantValue))
                this.value = constantValue;
        } catch (WrongExpressionException e) {
            throw new NonValidExpressionException("\nNon-valid constant value found: '" + constantValue + "'"
                    + e.getMessage());
        }
    }

    /**
     * Method to determine whether the given expression can be evaluated
     *
     * @param expression expression to check
     * @return evaluated expression if valid (as integer)
     * @throws WrongExpressionException if the given expression cannot be evaluated
     * @see AssemblyFunctions for the implementation details
     * @see AssemblyConstants for the valid interval
     */
    @Override
    protected Integer handleExpression(String expression) throws WrongExpressionException {
        if (expression == null || expression.equals(""))
            throw new WrongExpressionException("\nNull-pointer integer expression found");

        expression = expression.replaceAll("_", "");

        try {
            int value = AssemblyFunctions.eval(expression);
            if (isRepresentable(value))
                return value;
        } catch (NonImplementedFunctionalityException e) {
            throw new WrongExpressionException("\n'" + expression + "' is not a valid expression" + e.getMessage());
        }

        return null;
    }

    /**
     * Method to determine whether the given integer value is representable
     *
     * @param value value to check
     * @return true if representable with given architecture
     * @throws WrongExpressionException if the given value cannot be represented in the given architecture
     */
    @Override
    protected boolean isRepresentable(Integer value) throws WrongExpressionException {
        if (value > AssemblyConstants.SGN_INTEGER_MAX_VALUE
                || value < AssemblyConstants.SGN_INTEGER_MIN_VALUE)
            throw new WrongExpressionException("\n'" + value + "' cannot be fitted in "
                    + AssemblyConstants.BIT_ARCHITECTURE + "-bit architecture" +
                    "\nAllowed values: [" + AssemblyConstants.SGN_INTEGER_MIN_VALUE
                    + " .. " + AssemblyConstants.SGN_INTEGER_MAX_VALUE + "]");

        return true;
    }
}
