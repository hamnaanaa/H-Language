package Assembly.AssemblyInstructions;

import Assembly.AssemblyTokens.Token;

/***
 * Abstract class that represents instructions of the TEXT-Section
 */
// TODO : TEXT Instruction
public abstract class TEXT_Instruction extends Instruction {
    public TEXT_Instruction(Token[] tokens) {
        super(tokens);
    }
}
