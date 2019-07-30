package Assembly.AssemblyInstructions;

import Assembly.AssemblyTokens.Token;

/***
 * Abstract class that represents instructions of the DATA-Section
 */
// TODO : DATA Instruction
public class DATA_Instruction extends Instruction {
    public DATA_Instruction(Token[] tokens) { ;
    }

    @Override
    protected Token[] parse(Token[] tokens) {
        return new Token[0];
    }
}
