package Assembly.AssemblyInstructions;


import Assembly.AssemblyConstants.Operators;
import Assembly.AssemblyExceptions.FunctionalExceptions.NonValidAssemblyInstructionException;
import Assembly.AssemblyExceptions.InstructionParserExceptions.ParserException;
import Assembly.AssemblyTokens.*;
import Assembly.InstructionParser;

import javax.swing.text.html.parser.Parser;
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
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Token token : this.tokens)
            builder.append(token.getTokenName()).append(" '").append(token).append("' ");

        return builder.toString();
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
        } catch (NoSuchMethodException | IllegalAccessException e) {
            // TODO : change exceptions
            throw new ParserException(e.getMessage());
        } catch (InvocationTargetException e) {
            throw new ParserException(e.getTargetException().getMessage());
        }

    }

    /*                                          BEGIN: ARITY 1 INSTRUCTIONS                                           */

    /**
     * Method to parse an instruction out of single operator token
     * Checks if the given token has valid arity
     *
     * @param operatorToken operator token to parse
     * @return true if successful (valid arity)
     * @throws NonValidAssemblyInstructionException if the given token has arity unequal zero
     */
    private boolean parse(OperatorToken operatorToken) throws NonValidAssemblyInstructionException {
        if ((operatorToken.getOP().getArity()) == 0)
            return true;
        else
            throw new NonValidAssemblyInstructionException("\nWrong operator found:" +
                    "\nExpected arity '0', but the given operator token '"
                    + operatorToken + "' has arity " + operatorToken.getOP().getArity());
    }

    /**
     * Method to parse an instruction out of entry label token
     * Notifies the parser to check for duplicates and add the label to the map (for address specification later)
     *
     * @param entryLabelToken entry label token to parse
     * @return true if successful (not already specified)
     * @throws NonValidAssemblyInstructionException if the given entry label was already specified (duplicate)
     */
    private boolean parse(EntryLabelToken entryLabelToken) throws NonValidAssemblyInstructionException {
        parser.notify(entryLabelToken, this);

        return true;
    }
    /*                                            END: ARITY 1 INSTRUCTIONS                                           */

    /*                                          BEGIN: ARITY 2 INSTRUCTIONS                                           */
    // TODO : javadoc
    private boolean parse(OperatorToken operatorToken, RegistryAccessToken registryAccessToken) {
        switch (operatorToken.getOP()) {
            /* PRINT: print the given registry's value on display */
            case PRINT:
                /*PUSH: push the given registry's value on stack */
            case PUSH:
                /*POP: pop current value from stack into the given registry */
            case POP:
                /* INC: increment the given registry's value */
            case INC:
                /* NOT: invert the given registry's value */
            case NOT:
                /* CALL: call an address specified in the given registry */
            case CALL:
                /* JCC: jump to an address specified in the given registry */
            case JMP:
            case JZ:
            case JE:
            case JNZ:
            case JNE:
            case JOVF:
            case JLE:
            case JNG:
            case JL:
            case JNGE:
                return true;
            default:
                throw new NonValidAssemblyInstructionException("\nInvalid operator combination found:"
                        + "\n" + operatorToken.getTokenName() + " " + registryAccessToken.getTokenName() + " is not supported by H");
        }
    }

    // TODO : javadoc
    private boolean parse(OperatorToken operatorToken, StringLiteralToken stringLiteral) {
        switch (operatorToken.getOP()) {

        }

        // TODO
        return false;
    }
}
