package Assembly.AssemblyInstructions;

import Assembly.AssemblyTokens.Token;

/***
 * Abstract class that represents instructions of the BSS-Section
 */
// TODO : BSS_Instruction
public class BSS_Instruction extends Instruction {
    @Override
    protected Token[] parse(Token[] tokens) {
        return new Token[0];
    }
}
