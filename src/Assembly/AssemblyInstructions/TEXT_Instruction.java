package Assembly.AssemblyInstructions;


import Assembly.AssemblyExceptions.FunctionalExceptions.NonValidAssemblyInstructionException;
import Assembly.AssemblyExceptions.InstructionParserExceptions.ParserException;
import Assembly.AssemblyTokens.EntryLabelToken;
import Assembly.AssemblyTokens.OperatorToken;
import Assembly.AssemblyTokens.Token;
import Assembly.InstructionParser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/***
 * Abstract class that represents instructions of the TEXT-Section
 */
// TODO : TEXT Instruction
public class TEXT_Instruction extends Instruction {

    private InstructionParser parser;

    // TODO : reflection for each arity
    public TEXT_Instruction(Token[] tokens, InstructionParser parser) {
        if (parser == null)
            throw new ParserException("Null-pointer parser found");
        this.parser = parser;

        this.tokens = parse(tokens);

    }

    @Override
    protected Token[] parse(Token[] tokens) {
        if (tokens == null || tokens.length == 0)
            throw new IllegalArgumentException("\nNull-pointer token found");

        return parse(tokens, tokens.length);
    }

    private Token[] parse(Token[] tokens, int tokensNum) {
        try {
            Method specificParseMethod;

            switch (tokensNum) {
                case 1:
                    specificParseMethod = this.getClass().getDeclaredMethod("parse",
                            tokens[0].getClass());
                    specificParseMethod.setAccessible(true);
                    if ((boolean) specificParseMethod.invoke(this, tokens[0]))
                        return tokens;
                case 2:
                    specificParseMethod = this.getClass().getDeclaredMethod("parse",
                            tokens[0].getClass(), tokens[1].getClass());
                    specificParseMethod.setAccessible(true);
                    if ((boolean) specificParseMethod.invoke(this, tokens[0], tokens[1]))
                        return tokens;
                case 3:
                    specificParseMethod = this.getClass().getDeclaredMethod("parse",
                            tokens[0].getClass(), tokens[1].getClass(), tokens[2].getClass());
                    specificParseMethod.setAccessible(true);
                    if ((boolean) specificParseMethod.invoke(this, tokens[0], tokens[1], tokens[2]))
                        return tokens;
                default:
                    throw new UnsupportedOperationException("\nNon-valid instruction found:\n" +
                            "Non-valid function arity: " + tokens.length);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // TODO : change exceptions
            e.printStackTrace();
        }

        throw new UnsupportedOperationException("\nNon-valid instruction found");
    }

    // TODO ; javadoc
    private boolean parse(OperatorToken operatorToken) {
        if ((operatorToken.getOP().getArity()) == 0)
            return true;
        else
            throw new NonValidAssemblyInstructionException("\nWrong operator found:" +
                    "\nExpected arity '0', but the given operator token '"
                    + operatorToken + "' has arity " + operatorToken.getOP().getArity());
    }

    // TODO : javadoc
    private boolean parse(Assembly.AssemblyTokens.EntryLabelToken entryLabelToken) {
        parser.notify(entryLabelToken, this);

        return true;
    }

}
