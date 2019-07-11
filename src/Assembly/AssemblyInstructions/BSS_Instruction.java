package Assembly.AssemblyInstructions;

import Assembly.AssemblyTokens.Token;

/***
 * Abstract class that represents instructions of the BSS-Section
 */
// TODO : BSS_Instruction
public abstract class BSS_Instruction extends Instruction {
    public BSS_Instruction(Token[] tokens) {
        super(tokens);
    }
}
