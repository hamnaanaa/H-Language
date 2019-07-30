package Assembly.AssemblyTokens;

import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions.WrongExpressionException;
import Assembly.AssemblyFunctionality.AssemblyFunctions;

/**
 * Abstract class that represents different expression tokens in H-Language assembly instructions
 *
 * @param <T> any subclass of Number can be an expression
 */
public abstract class ExpressionToken<T extends Number> extends Token<T> {
    /**
     * Method to determine whether the given expression can be evaluated
     *
     * @param expression expression to check
     * @return evaluated expression if valid (as integer)
     * @throws WrongExpressionException if the given expression cannot be evaluated
     * @see AssemblyFunctions for the implementation details
     * @see AssemblyConstants for the valid interval
     */
    protected abstract T handleExpression(String expression);

    /**
     * Method to determine whether the given integer value is representable
     *
     * @param value value to check
     * @return true if representable with given architecture
     * @throws WrongExpressionException if the given value cannot be represented in the given architecture
     */
    protected abstract boolean isRepresentable(T value);
}

