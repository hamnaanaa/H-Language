package Assembly.AssemblyFunctionality;

import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.AssemblyConstants.Operators;
import Assembly.AssemblyConstants.Alphabets;
import Assembly.AssemblyExceptions.FunctionalExceptions.*;
import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidAccessLabelException;
import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidExpressionException;
import Assembly.AssemblyExceptions.FunctionalExceptions.WrongEntryLabelException;
import Assembly.AssemblyTokens.AccessLabelToken;
import Assembly.AssemblyTokens.IntegerExpressionToken;
import Assembly.AssemblyTokens.Token;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Arrays;

/**
 * Class that represents all functions of Assembly
 */
public class AssemblyFunctions {
    /**
     * Method to determine whether the given registry name is valid
     *
     * @param registryName registry name to check
     * @return is the given registry name in the list of valid registry names
     * @throws WrongRegistryNameException if given registry name is not valid (was not found in the list)
     * @see AssemblyConstants for the list of valid registry names
     */
    public static boolean isRegistryName(String registryName) throws WrongRegistryNameException {
        if (registryName == null)
            throw new WrongRegistryNameException("\nNon-valid registry name found"
                    + "'\nAvailable registry names: " + AssemblyConstants.getValidRegistryNames());

        if (Arrays
                .asList(AssemblyConstants.VALID_REGISTRY_NAMES)
                .contains(registryName))
            return true;

        throw new WrongRegistryNameException("\nNon-valid registry name found: '" + registryName
                + "'\nAvailable registry names: " + AssemblyConstants.getValidRegistryNames());
    }

    /**
     * Method to determine whether the given registry index is valid
     *
     * @param registryIndex Integer that represents a registry index to check
     * @return true if the registry index is valid or exception
     * @throws WrongRegistryIndexException if the given registry index is not in the allowed interval
     * @see AssemblyConstants for the allowed interval
     */
    public static boolean isRegistryIndex(int registryIndex) throws WrongRegistryIndexException {
        if (registryIndex < AssemblyConstants.MIN_REGISTRY_INDEX
                || registryIndex > AssemblyConstants.MAX_REGISTRY_INDEX)
            throw new WrongRegistryIndexException("\nNon-valid registry index found: '" + registryIndex
                    + "'\nAvailable registry indices: " + AssemblyConstants.getValidRegistryIndices());

        return true;
    }

    /**
     * Method to evaluate the given expression
     *
     * @param expression String to evaluate as expression
     * @return int value of the given expression
     * @throws WrongExpressionException             if the given expression cannot be evaluated as int
     * @throws NonImplementedFunctionalityException if the given expression is a floating point number
     */
    public static int eval(String expression) throws WrongExpressionException, NonImplementedFunctionalityException {
        // TODO : reverse polish notation for eval()
        try {
            ScriptEngineManager evaluator = new ScriptEngineManager();
            ScriptEngine engine = evaluator.getEngineByName("js");
            Object result = engine.eval(expression);
            try {
                return (int) result;
            } catch (ClassCastException e) {
                if (result.getClass().toString().equals("class java.lang.Double"))
                    throw new NonImplementedFunctionalityException("\nFloating point numbers are not supported yet :("
                            + "\nWork in progress: Thanks for supporting H-Language!");
                else
                    throw new WrongExpressionException("\nGiven expression '" + expression +
                            "' cannot be evaluated as int");
            }

        } catch (ScriptException | NullPointerException e) {
            throw new WrongExpressionException("\nGiven expression '" + expression + "' cannot be evaluated as int");
        }
    }

    // TODO : docs
    public static Token[] tokenize(String line) {
        // TODO : tokenize (maybe a separate class makes more sense?)
        return null;
    }

    /**
     * Method to determine whether the given string is an operator
     *
     * @param OP string to check
     * @return operator matching the given OP string
     * @throws WrongOperatorNameException if no operator matching the given OP string found
     */
    public static Operators getOperator(String OP) throws WrongOperatorNameException {
        return Operators.getOperator(OP);
    }

    /**
     * Method to determine whether the given string is a valid name literal
     *
     * @param nameLiteral name literal to check
     * @return true if the given name literal is a valid name literal
     * @see AssemblyConstants for the valid alphabets
     * @see Alphabets for the definition of a valid alphabet and the implementation of the methods
     */
    public static boolean isNameLiteral(String nameLiteral) {
        if (nameLiteral == null || nameLiteral.equals(""))
            return false;

        char[] chars = nameLiteral.toCharArray();
        for (int i = 0; i < chars.length; i++)
            if (!AssemblyConstants.VALID_CHARS.contains(chars[i])
                    || (i == 0 && AssemblyConstants.DECIMAL.contains(chars[i])))
                return false;

        return true;
    }

    /**
     * Method to determine whether the given access label is a valid access label
     * Checks if the access label was opened and closed correctly and if the label is a valid name literal
     *
     * @param accessLabel access label to check
     * @return true if the given access label is a valid access label
     * @throws WrongAccessLabelException if the given access label was not opened or closed correctly
     * @see AssemblyConstants for valid access separators (open and close)
     */
    public static boolean isAccessLabel(String accessLabel) throws WrongAccessLabelException {
        if (accessLabel == null || accessLabel.equals(""))
            return false;

        if (accessLabel.charAt(0) != AssemblyConstants.ACCESS_SEPARATOR_OPEN)
            throw new WrongAccessLabelException("\nAccess label '" + accessLabel + "' was not opened correctly: "
                    + "'" + AssemblyConstants.ACCESS_SEPARATOR_OPEN + "' is missing at the beginning");

        if (accessLabel.charAt(accessLabel.length() - 1) != AssemblyConstants.ACCESS_SEPARATOR_CLOSE)
            throw new WrongAccessLabelException("\nAccess label '" + accessLabel + "' was not closed correctly: "
                    + "'" + AssemblyConstants.ACCESS_SEPARATOR_CLOSE + "' is missing at the end");


        return isNameLiteral(accessLabel.substring(1, accessLabel.length() - 1));
    }

    /**
     * Method to extract a pair of two tokens from an access label with index
     *
     * @param accessLabel access label with index to extract access label and index from
     * @return pair of access label token and integer expression token
     * @throws NonValidAccessLabelException if the number of tokens is not valid or tokens are not formatted correctly
     * @see AssemblyConstants for the valid separators
     * @see AccessLabelToken for the valid access label token
     * @see IntegerExpressionToken for the valid integer expression token
     */
    public static Pair<AccessLabelToken, IntegerExpressionToken> extractAccessLabelWithIndexTokens(String accessLabel)
            throws NonValidAccessLabelException {
        String[] tokens = accessLabel.split("(?=\\" + AssemblyConstants.INDEX_SEPARATOR_OPEN + ")");

        if (tokens.length != 2)
            throw new WrongAccessLabelException("\nAccess label with index must consist of two tokens. Tokens found:\n"
                    + Arrays.toString(tokens));

        if (tokens[1].length() < 2)
            throw new WrongAccessLabelException("\nWrong access label index found: '" + tokens[1] + "'");
        if (!tokens[1].startsWith(String.valueOf(AssemblyConstants.INDEX_SEPARATOR_OPEN)))
            throw new WrongAccessLabelException("\nAccess label index '" + tokens[1] + "' was not opened correctly: "
                    + "'" + AssemblyConstants.INDEX_SEPARATOR_OPEN + "' is missing at the beginning");
        if (!tokens[1].endsWith(String.valueOf(AssemblyConstants.INDEX_SEPARATOR_CLOSE)))
            throw new WrongAccessLabelException("\nAccess label index '" + tokens[1] + "' was not closed correctly: "
                    + "'" + AssemblyConstants.INDEX_SEPARATOR_CLOSE + "' is missing at the end");

        try {
            String expressionToEvaluate = tokens[1].substring(1, tokens[1].length() - 1);
            return new Pair<>(new AccessLabelToken(tokens[0]), new IntegerExpressionToken(expressionToEvaluate));
        } catch (NonValidExpressionException e) {
            throw new WrongAccessLabelException(e.getMessage());
        }
    }

    /**
     * Method to determine whether the given jump label is a valid jump label
     * Checks if the jump label was opened and closed correctly and if the label is a valid name literal
     *
     * @param jumpLabel jump label to check
     * @return true if the given jump label is a valid jump label
     * @throws WrongJumpLabelException if the given jump label was not opened or closed correctly
     * @see AssemblyConstants for valid jump label separators (open and close)
     */
    public static boolean isJumpLabel(String jumpLabel) throws WrongJumpLabelException {
        if (jumpLabel == null || jumpLabel.equals(""))
            return false;

        if (jumpLabel.charAt(0) != AssemblyConstants.JUMP_LABEL_SEPARATOR_OPEN)
            throw new WrongJumpLabelException("\nJump label '" + jumpLabel + "' was not opened correctly: "
                    + "'" + AssemblyConstants.JUMP_LABEL_SEPARATOR_OPEN + "' is missing at the beginning");

        if (jumpLabel.charAt(jumpLabel.length() - 1) != AssemblyConstants.JUMP_LABEL_SEPARATOR_CLOSE)
            throw new WrongJumpLabelException("\nJump label '" + jumpLabel + "' was not closed correctly: "
                    + "'" + AssemblyConstants.JUMP_LABEL_SEPARATOR_CLOSE + "' is missing at the end");

        return isNameLiteral(jumpLabel.substring(1, jumpLabel.length() - 1));
    }

    /**
     * Method to determine whether the given entry label is a valid entry label
     * Checks if the entry label is long enough, was opened and closed correctly and if the label is a valid name literal
     *
     * @param entryLabel entry label to check
     * @return true if the given entry label is a valid entry label
     * @throws WrongEntryLabelException if the given entry label was not opened or closed correctly
     *                                  OR if the given entry label is not long enough
     *                                  OR if space is missing between entry label separator open and name literal token
     * @see AssemblyConstants for the valid entry label separators (open and close)
     */
    public static boolean isEntryLabel(String entryLabel) throws WrongEntryLabelException {
        if (entryLabel == null || entryLabel.equals(""))
            return false;

        if (entryLabel.length() < 4)
            throw new WrongEntryLabelException("\nEntry label '" + entryLabel + "' is too short and cannot be correct");

        if (!entryLabel.substring(0, 2).equals(AssemblyConstants.ENTRY_LABEL_SEPARATOR_OPEN))
            throw new WrongEntryLabelException("\nEntry label '" + entryLabel + "' was not opened correctly: "
                    + "'" + AssemblyConstants.ENTRY_LABEL_SEPARATOR_OPEN + "' is missing at the beginning");
        if (entryLabel.charAt(entryLabel.length() - 1) != AssemblyConstants.ENTRY_LABEL_SEPARATOR_CLOSE)
            throw new WrongEntryLabelException("\nEntry label '" + entryLabel + "' was not closed correctly: "
                    + "'" + AssemblyConstants.ENTRY_LABEL_SEPARATOR_CLOSE + "' is missing at the end");
        if (entryLabel.charAt(2) != ' ')
            throw new WrongEntryLabelException("\nEntry label requires a space between the" +
                    " open separator and the label\nEntry label found: '" + entryLabel + "'");

        return isNameLiteral(entryLabel.substring(3, entryLabel.length() - 1));
    }

    // TODO
    /**
     * Method to determine whether the given string literal is a valid string literal
     * Checks if the string literal is long enough, was opened and closed correctly and the string is a valid string
     * (correctly used escape sequences and separators)
     *
     * @param stringLiteral string literal to check
     * @return string if the given string literal is correct without separators
     * @throws WrongStringLiteralException
     */
    public static String formatStringLiteral(String stringLiteral) throws WrongStringLiteralException {
        if (stringLiteral == null || stringLiteral.equals(""))
            throw new WrongStringLiteralException("\nNull-pointer string literal found");

        if (stringLiteral.length() < 2)
            throw new WrongStringLiteralException("\nWrong string literal found: '" + stringLiteral
                    + "'\nThis string literal is too short and cannot be correct");

        if (stringLiteral.charAt(0) == AssemblyConstants.CHAR_SEPARATOR
                && stringLiteral.charAt(stringLiteral.length() - 1) == AssemblyConstants.CHAR_SEPARATOR)
            return String.valueOf(formatCharLiteral(stringLiteral));

        if (stringLiteral.charAt(0) != AssemblyConstants.STRING_SEPARATOR)
            throw new WrongStringLiteralException("\nString literal '" + stringLiteral + "' was not opened correctly: "
                    + "'" + AssemblyConstants.STRING_SEPARATOR + "' is missing at the beginning");
        if (stringLiteral.charAt(stringLiteral.length() - 1) != AssemblyConstants.STRING_SEPARATOR)
            throw new WrongStringLiteralException("\nString literal '" + stringLiteral + "' was not closed correctly: "
                    + "'" + AssemblyConstants.STRING_SEPARATOR + "' is missing at the end");

        char[] characters = stringLiteral.substring(1, stringLiteral.length() - 1).toCharArray();
        StringBuilder formattedStringBuilder = new StringBuilder();
        boolean isEscaped = false;

        for (char character : characters) {
            if (!isEscaped) {
                if (character == AssemblyConstants.ESCAPE_SEQUENCE)
                    isEscaped = true;
                else if (character != AssemblyConstants.STRING_SEPARATOR)
                    formattedStringBuilder.append(character);
                else
                    throw new WrongStringLiteralException("\nString literal with wrong string separator "
                            + "(wasn't escaped) found:\n'" + stringLiteral + "'");
            } else {
                formattedStringBuilder.append(character);
                isEscaped = false;
            }
        }

        if (isEscaped)
            throw new WrongStringLiteralException("\nWrong string literal\nString separator at the end was escaped: '"
                    + stringLiteral + "'");

        return formattedStringBuilder.toString();
    }

    // TODO: javadoc
    private static char formatCharLiteral(String stringLiteral) throws WrongStringLiteralException {
        if (stringLiteral.length() > 4)
            throw new WrongStringLiteralException("\nChar literal can only be a single character, but found: '"
                    + stringLiteral + "'");

        if (stringLiteral.charAt(1) != AssemblyConstants.ESCAPE_SEQUENCE)
            if (stringLiteral.length() == 3)
                return stringLiteral.charAt(1);
            else
                throw new WrongStringLiteralException("\nChar literal can only be a single character, but found: '"
                        + stringLiteral + "'");
        else if (stringLiteral.length() == 4)
            return stringLiteral.charAt(2);
        else
            throw new WrongStringLiteralException("\nChar literal with single escape sequence found: '"
                    + stringLiteral + "'");
    }
}

