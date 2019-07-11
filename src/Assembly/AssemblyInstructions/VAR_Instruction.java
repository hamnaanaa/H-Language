package Assembly.AssemblyInstructions;

import Assembly.AssemblyTokens.Token;

/***
 * Abstract class that represents instructions of the VARIABLES-Section
 */
// TODO : VAR Instruction
public abstract class VAR_Instruction extends Instruction {
    public VAR_Instruction(Token[] tokens) {
        super(tokens);
    }
}
