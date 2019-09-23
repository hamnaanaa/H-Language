package Assembly.AssemblyInstructions;


import Assembly.AssemblyConstants.Operators;
import Assembly.AssemblyExceptions.FunctionalExceptions.NonValidAssemblyInstructionException;
import Assembly.AssemblyExceptions.InstructionParserExceptions.ParserException;
import Assembly.AssemblyTokens.*;
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
            e.getStackTrace();
        }

        throw new UnsupportedOperationException("\nNon-valid instruction found");
    }

    /*                                          BEGIN: ARITY 1 INSTRUCTIONS                                           */
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
    private boolean parse(EntryLabelToken entryLabelToken) {
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
