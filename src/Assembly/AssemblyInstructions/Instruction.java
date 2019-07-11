package Assembly.AssemblyInstructions;

import Assembly.AssemblyTokens.Token;

/***
 * Abstract class that represents instructions in H-Language assembly consisting of tokens
 */
// TODO : abstract class + docs
// TODO : interface (Binary)Instruction with all possible classes for the visitor pattern (don't forget reflection)
public abstract class Instruction {
    private Token[] tokens;

    public Instruction(Token[] tokens) {
        this.tokens = tokens;
    }
}
