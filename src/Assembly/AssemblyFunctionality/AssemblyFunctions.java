package Assembly.AssemblyFunctionality;

import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.AssemblyConstants.Operators;
import Assembly.AssemblyConstants.Alphabets;
import Assembly.AssemblyExceptions.FunctionalExceptions.*;
import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidAccessLabelException;
import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidExpressionException;
import Assembly.AssemblyExceptions.FunctionalExceptions.WrongEntryLabelException;
import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidOperatorException;
import Assembly.AssemblyExceptions.PreprocessorExceptions.PreproccessorException;
import Assembly.AssemblyTokens.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
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

    /**
     * Method to extract tokens out of the given string
     *
     * @param line string to extract tokens from
     * @return array of tokens if the given string was successfully tokenized
     * @throws WrongAssemblyLineException if error occurred while tokenizing the string or parsing into tokens
     */
    public static Token<?>[] tokenize(String line) throws WrongAssemblyLineException {
        return Tokenizer.tokenize(line, true);
    }

    /**
     * Special Method for testing purposes:
     * Extracts the tokens out of the given string as strings without parsing them into tokens
     *
     * @param line string to extract string tokens from
     * @return array of strings representing extracted tokens before parsing
     * @throws WrongAssemblyLineException if error occurred while tokenizing the string
     */
    public static String[] extractTokens(String line) throws WrongAssemblyLineException {
        return Tokenizer.extractRawTokens(line, true);
    }

    /**
     * Modified tokenize-Method for arrays to check and tokenize an array
     *
     * @param array array to check and tokenize
     * @return array of tokens if the given array is valid and was successfully tokenized
     * @throws WrongArrayException        if the given array is not valid
     * @throws WrongAssemblyLineException if error occurred while tokenizing the array and parsing it into tokens
     */
    public static Token<?>[] tokenizeArray(String array) throws WrongArrayException, WrongAssemblyLineException {
        if (array == null || array.length() < 2)
            throw new WrongArrayException("\nNull-pointer array found");

        if (array.startsWith(String.valueOf(AssemblyConstants.ARRAY_SEPARATOR_OPEN))
                && array.endsWith(String.valueOf(AssemblyConstants.ARRAY_SEPARATOR_CLOSE))) {
            return Tokenizer.tokenize(array.substring(1, array.length() - 1), false);
        }

        throw new WrongArrayException("\nThe given array '" + array + "' is not valid: Wrong separators found");
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

    /**
     * Method to determine whether the given string literal is a valid string literal
     * Checks if the string literal is long enough, was opened and closed correctly and the string is a valid string
     * (correctly used escape sequences and separators)
     *
     * @param stringLiteral string literal to check
     * @return string if the given string literal is correct without separators
     * @throws WrongStringLiteralException if the given string literal is not long enough to be valid
     *                                     OR if the given string literal was not opened or closed correctly
     *                                     OR if the given string literal contains illegal chars (wrongly escaped)
     *                                     OR if the close string literal separator was escaped
     * @see AssemblyConstants for the valid string literal separators
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

        // check for illegal characters and escape sequences
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

    /**
     * Method to determine whether the given char literal is a valid char literal
     * Checks if the char literal has correct length, was opened and closed correctly and the escape sequence is valid
     *
     * @param stringLiteral char literal to check
     * @return char literal if the given char literal is correct without separators
     * @throws WrongStringLiteralException if the given char literal is too long to be valid (not a single char)
     *                                     OR if the given char literal was not opened or closed correctly
     *                                     OR if the given char literal contains a single escape sequence
     * @see AssemblyConstants for the valid char literal separators
     */
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

/**
 * Private class, that tokenizes lines with H-Language Assembly instructions
 * - splits the line into string tokens
 * - parses the string tokens to assembly tokens
 */
class Tokenizer {
    public static Token<?>[] tokenize(String line, boolean spaceSeparator) throws WrongAssemblyLineException {
        String[] rawTokens = extractRawTokens(line, spaceSeparator);

        Token<?>[] parsedTokens = new Token<?>[rawTokens.length];
        for (int i = 0; i < rawTokens.length; i++) {
            Token<?> parsedToken = parseStringToken(rawTokens[i]);
            parsedTokens[i] = (parsedToken);
        }

        return parsedTokens;
    }

    /**
     * Method to parse a raw string token into a token object
     * Idea how to recognize the token (sequentially):
     * - array begins with '['
     * - string/char begins with '"' or '"'
     * - access label with index begins with '<' and ends with ']'
     * - access label begins with '<'
     * - entry label begins with '%%'
     * - jump label begins with '%'
     * - registry access contains ':'
     * - expression begins with '(', '-' or digit
     * - default:
     * -- try to parse as a reserved word
     * -- if not possible try as a variable
     *
     * @param rawToken raw string token to parse from
     * @return subclass of token if the given raw string token is parsable (valid token)
     * @throws WrongAssemblyLineException if error occurred while parsing the raw string token into token
     */
    private static Token<?> parseStringToken(String rawToken) throws WrongAssemblyLineException {
        if (rawToken == null || rawToken.equals(""))
            throw new WrongAssemblyLineException("\nNull-pointer token found");

        try {
            switch (rawToken.charAt(0)) {
                // array
                case AssemblyConstants.ARRAY_SEPARATOR_OPEN:
                    return new ArrayToken(rawToken);

                // string/char
                case AssemblyConstants.CHAR_SEPARATOR:
                case AssemblyConstants.STRING_SEPARATOR:
                    return new StringLiteralToken(rawToken);

                // access label with/without index
                case AssemblyConstants.ACCESS_SEPARATOR_OPEN:
                    return rawToken.endsWith(String.valueOf(AssemblyConstants.INDEX_SEPARATOR_CLOSE))
                            ? new AccessLabelWithIndexToken(rawToken)
                            : new AccessLabelToken(rawToken);

                // entry/jump label
                case AssemblyConstants.JUMP_LABEL_SEPARATOR_OPEN:
                    return rawToken.length() > 1 && rawToken.substring(0, 2)
                            .equals(AssemblyConstants.ENTRY_LABEL_SEPARATOR_OPEN)
                            ? new EntryLabelToken(rawToken)
                            : new JumpLabelToken(rawToken);

                // expression
                case AssemblyConstants.PARENTHESIS_OPEN:
                    return new IntegerExpressionToken(rawToken);

                default:
                    // constant expression
                    if (AssemblyConstants.isNumeric(rawToken.charAt(0)))
                        return new IntegerExpressionToken(rawToken);

                        // registry access
                    else if (rawToken.contains(":"))
                        return new RegistryAccessToken(rawToken);
                    else
                        // reserved word
                        try {
                            return new OperatorToken(rawToken);
                            // name literal (variable)
                        } catch (NonValidOperatorException e) {
                            return new NameLiteralToken(rawToken);
                        }
            }
            // unified exception
        } catch (PreproccessorException e) {
            throw new WrongAssemblyLineException("\nError in token '" + rawToken + "' occurred:"
                    + e.getMessage());
        }
    }

    /**
     * Method to split the given string line representing an H-Language Assembly instruction into string tokens
     *
     * @param line line that represents an H-Language Assembly instruction to split
     * @return array of string tokens extracted from the given line
     * @throws WrongAssemblyLineException if some wrong tokens/separators found
     * @format see Tokenizer.pdf for the flow diagram
     */
    public static String[] extractRawTokens(String line, boolean spaceSeparator) throws WrongAssemblyLineException {
        ArrayList<String> stringTokens = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        char[] chars = line.toCharArray();

        // Monitor different possible special states
        char prevChar = '\0';
        int arraySeparatorsBalance = 0, parenthesisBalance = 0;
        boolean isString = false, isChar = false, isLabelSeparator = false, isJumpLabel = false, isSeparable = true;

        // Check sequentially the characters
        for (char character : chars) {
            // Two different branches: isSeparable or not
            if ((!AssemblyConstants.isWhitespace(character) && character != AssemblyConstants.OPERATOR_SEPARATOR)
                    || !isSeparable) {
                // if not separable, new two branches: isString or not
                if (isString || isChar) {
                    switch (character) {
                        // if isString, monitor string close separator
                        case AssemblyConstants.STRING_SEPARATOR:
                            if (!isChar)
                                if (prevChar != AssemblyConstants.ESCAPE_SEQUENCE)
                                    isString = false;
                            break;

                        case AssemblyConstants.CHAR_SEPARATOR:
                            if (!isString)
                                if (prevChar != AssemblyConstants.ESCAPE_SEQUENCE)
                                    isChar = false;
                            break;

                        // everything can be added to the string
                        default:
                            break;
                    }
                } else {
                    // if not a string part, check all other separators and states
                    switch (character) {
                        case AssemblyConstants.STRING_SEPARATOR:
                            isString = true;
                            break;
                        case AssemblyConstants.CHAR_SEPARATOR:
                            isChar = true;
                            break;

                        case AssemblyConstants.ARRAY_SEPARATOR_OPEN:
                            arraySeparatorsBalance++;
                            break;
                        case AssemblyConstants.ARRAY_SEPARATOR_CLOSE:
                            arraySeparatorsBalance--;
                            if (arraySeparatorsBalance < 0)
                                throw new WrongAssemblyLineException("\nWrong assembly line found:\n"
                                        + "'" + line + "' contains too many index accesses/array close separators");
                            break;

                        case AssemblyConstants.PARENTHESIS_OPEN:
                            parenthesisBalance++;
                            break;
                        case AssemblyConstants.PARENTHESIS_CLOSE:
                            parenthesisBalance--;
                            if (parenthesisBalance < 0)
                                throw new WrongAssemblyLineException("\nWrong assembly line found:\n"
                                        + "'" + line + "' contains too many close parenthesis");
                            break;

                        case AssemblyConstants.ACCESS_SEPARATOR_OPEN:
                            isLabelSeparator = true;
                            break;
                        case AssemblyConstants.ACCESS_SEPARATOR_CLOSE:
                            if (isLabelSeparator)
                                isLabelSeparator = false;
                            else
                                throw new WrongAssemblyLineException("\nWrong assembly line found:\n"
                                        + "'" + line + "' contains too many access label close separators");
                            break;

                        case AssemblyConstants.JUMP_LABEL_SEPARATOR_OPEN:
                            isJumpLabel = true;
                            break;
                        case AssemblyConstants.JUMP_LABEL_SEPARATOR_CLOSE:
                            isJumpLabel = false;
                            break;

                        default:
                            break;
                    }
                }

                // add the final character if everything is fine
                buffer.append(character);

                // modify the current state
                isSeparable = !isString && !isChar && parenthesisBalance == 0 && arraySeparatorsBalance == 0
                        && !isLabelSeparator && !isJumpLabel;

            } else {

                // if was separated, add the existing buffer as a token
                if (spaceSeparator || character == AssemblyConstants.OPERATOR_SEPARATOR) {
                    if (buffer.length() > 0)
                        stringTokens.add(buffer.toString());
                    buffer.setLength(0);
                }
            }

            // Monitor the previous char
            prevChar = character;
        }

        // Add the last piece
        if (buffer.length() > 0)
            stringTokens.add(buffer.toString());

        String[] allTokens = new String[stringTokens.size()];
        for (int i = 0; i < allTokens.length; i++)
            allTokens[i] = stringTokens.get(i);

        // Check if the line was closed correctly
        if (!isSeparable)
            throw new WrongAssemblyLineException("\nWrong assembly line found:\n"
                    + "'" + line + "' contains unclosed tokens");

        return allTokens;
    }


}

