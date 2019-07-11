package Assembly.AssemblyInstructions;


import Assembly.AssemblyTokens.Token;

/***
 * Abstract class that represents instructions of the INCLUDE-Section
 */
// TODO : INCLUDE Instruction
public abstract class INCLUDE_Instruction extends Instruction {
    public INCLUDE_Instruction(Token[] tokens) {
        super(tokens);
    }
}
