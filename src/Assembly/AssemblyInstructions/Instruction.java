package Assembly.AssemblyInstructions;

import Assembly.AssemblyTokens.Token;

/***
 * Abstract class that represents instructions in H-Language assembly consisting of tokens
 */
// TODO : javadoc
// TODO : interface (Binary)Instruction with all possible classes for the visitor pattern (don't forget reflection)
public abstract class Instruction {
    protected Token[] tokens;

    protected abstract Token[] parse(Token[] tokens);
}
