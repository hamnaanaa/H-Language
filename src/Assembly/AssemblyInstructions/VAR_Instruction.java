package Assembly.AssemblyInstructions;

import Assembly.AssemblyTokens.Token;

/***
 * Abstract class that represents instructions of the VARIABLES-Section
 */
// TODO : VAR Instruction
public class VAR_Instruction extends Instruction {

    @Override
    protected Token[] parse(Token[] tokens) {
        return new Token[0];
    }
}
