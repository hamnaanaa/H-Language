package Assembly.AssemblyInstructions;

import Assembly.AssemblyTokens.Token;

/***
 * Abstract class that represents instructions of the DATA-Section
 */
// TODO : DATA Instruction
public abstract class DATA_Instruction extends Instruction {
    public DATA_Instruction(Token[] tokens) {
        super(tokens);
    }
}
