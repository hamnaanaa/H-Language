package Assembly;

import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.AssemblyExceptions.FunctionalExceptions.WrongAssemblyLineException;
import Assembly.AssemblyExceptions.FunctionalExceptions.WrongCommentFormatException;
import Assembly.AssemblyExceptions.FunctionalExceptions.WrongFilePathException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Class that represents the second step of the Assembly process:
 * -> format the given raw lines into a readable for Assembly source code without comments and whitespaces and lowercase
 */
public class CodeFormatter {
    /**
     * ArrayList of Strings containing all formatted lines without comments
     */
    private ArrayList<String> formattedCode;

    /**
     * Get-method for all formatted lines of CodeFormatter
     *
     * @return all formatted lines of this CodeFormatter
     */
    public ArrayList<String> getFormattedCode() {
        return formattedCode;
    }

    /**
     * Constructor for CodeFormatter, that takes file path as input to read all lines and format them accordingly
     *
     * @param filePath path of the file to read
     * @throws IOException @see RawInputReader for details
     */
    public CodeFormatter(String filePath) throws IOException {
        if (filePath == null)
            throw new WrongFilePathException("\nNon-valid path for a file");

        this.formattedCode = new RawInputReader(filePath).getAllLines();
        formatCode();

    }

    /**
     * Constructor for CodeFormatter TESTING, that takes a string array of instructions to format
     *
     * @param instructionsToTest string array of instructions to format
     * @format see tests
     */
    public CodeFormatter(String[] instructionsToTest) {
        this.formattedCode = new ArrayList<>(Arrays.asList(instructionsToTest));
        formatCode();
    }

    /***
     * Attribute for correct parsing of long (multiple lines) comments
     */
    private boolean isLongComment;

    /**
     * Method to extract source code from the given raw Lines:
     * - ignore single- and multiple-lined comments
     * - clear all unnecessary whitespace
     *
     * @see RawInputReader for the first step of the Assembly process (reading the file)
     */
    private void formatCode() {
        isLongComment = false;

        for (int i = 0; i < formattedCode.size(); i++) {
            String line = formattedCode.get(i);
            // format each line
            String handledLine = handleLine(line);

            // if not empty, replace given line with its formatted version
            if (handledLine != null && !handledLine.equals(""))
                formattedCode.set(i, handledLine);
            else
                // else remove the line without any instructions
                formattedCode.remove(i--);
        }
    }

    /***
     * Method to handle lines of codes with comments. Remove all comments and make sure there is no extra whitespace
     * @param line line with assembly instructions and comments to format
     * @return formatted line withoud any comments and with correct whitespaces
     */
    private String handleLine(String line) {
        if (line == null)
            throw new WrongCommentFormatException("\nNULL line was found");

        // bracketsState shows if the line already begins as a comment (for multiple line comments)
        int bracketsState = isLongComment ? 1 : 0;

        // if not a long comment and no long comment open separator found, check if there is any single line comment
        if (!isLongComment && !line.contains(Character.toString(AssemblyConstants.LONG_COMMENT_OPEN))) {
            // if there is a single line comment as well, it must be removed. Remove whitespaces afterwards
            if (!line.contains(Character.toString(AssemblyConstants.LONG_COMMENT_CLOSE)))
                return removeWhitespace(line.contains(Character.toString(AssemblyConstants.SINGLE_LINE_COMMENT))
                        ? line.substring(0, line.indexOf(AssemblyConstants.SINGLE_LINE_COMMENT))
                        : line);
            else
                return handleMixedLine(line, bracketsState);
        }

        // if a long comment and no long comment close separator found, return null (the whole line is a comment)
        if (isLongComment && !line.contains(Character.toString(AssemblyConstants.LONG_COMMENT_CLOSE)))
            return null;

        // handle special mixed lines with multiple comments possible
        return handleMixedLine(line, bracketsState);
    }

    /**
     * Method to handle lines that might contain complex combinations of different brackets and instructions in-between
     *
     * @param line          line to format
     * @param bracketsState state of brackets at the begin of the line (does this line begin as a part of long comment?)
     * @return formatted line with no comments and unnecessary whitespaces
     * @format see tests
     */
    private String handleMixedLine(String line, int bracketsState) {
        // instructions to extract between comments
        StringBuilder nonCommentLines = new StringBuilder();
        char prevChar = '\0';
        boolean isString = false;

        for (int i = 0; i < line.length(); i++) {
            // if long comment open bracket found, change the current state
            // if no escape sequence found and not a String
            if (line.charAt(i) == AssemblyConstants.LONG_COMMENT_OPEN
                    && prevChar != AssemblyConstants.ESCAPE_SEQUENCE && !isString) {
                bracketsState = 1;

                // if long comment close bracket found, change the current state
                // if no escape sequence found and not a String
            } else if (line.charAt(i) == AssemblyConstants.LONG_COMMENT_CLOSE
                    && prevChar != AssemblyConstants.ESCAPE_SEQUENCE && !isString) {
                bracketsState--;

                // make sure there is not too many long comment close brackets
                if (bracketsState < 0)
                    throw new WrongCommentFormatException("\nInvalid long comment found: " +
                            "Too many long comment close brackets found in line: '" + line + "'");

                // if state equals zero, extract all instructions found
            } else if (bracketsState == 0)
                // if single line comment found, break the loop (everything else is definitely a part of the comment)
                // if no escape sequence found and not a String
                if (line.charAt(i) == AssemblyConstants.SINGLE_LINE_COMMENT
                        && prevChar != AssemblyConstants.ESCAPE_SEQUENCE && !isString)
                    break;
                else
                    // add the instruction part found
                    nonCommentLines.append(line.charAt(i));

            // check if the string separator found and is not with an escape sequence
            if (line.charAt(i) == AssemblyConstants.STRING_SEPARATOR
                    && prevChar != AssemblyConstants.ESCAPE_SEQUENCE)
                isString = !isString;
            // save the previous char
            prevChar = line.charAt(i);
        }

        // update the brackets' state
        isLongComment = bracketsState != 0;

        // remove all unnecessary whitespaces found in lines in-between
        return removeWhitespace(nonCommentLines.toString());

    }

    /***
     * Method that removes all whitespaces found and separates each token with exactly one space
     * @param lineToFormat line to search for whitespace chars in
     * @return formatted line without unnecessary whitespaces
     * @see AssemblyConstants for isWhitespace()-method
     */
    private String removeWhitespace(String lineToFormat) {
        StringBuilder lineWithoutWhitespace = new StringBuilder();
        StringBuilder buffer = new StringBuilder();
        boolean isString = false;
        char prevChar = '\0';

        for (int i = 0; i < lineToFormat.length(); i++) {
            // check if the current char is a whitespace char
            if (AssemblyConstants.isWhitespace(lineToFormat.charAt(i))) {
                if (buffer.length() > 0) {
                    // add everything from buffer in-between
                    lineWithoutWhitespace.append(buffer).append(" ");
                    buffer.setLength(0);
                }
            } else {
                // add all non-whitespace chars found to the buffer
                char charToAdd = lineToFormat.charAt(i);
                // check if the char is a part of a string (check if it's not an escaped string separator as well)
                if (charToAdd == AssemblyConstants.STRING_SEPARATOR && prevChar != AssemblyConstants.ESCAPE_SEQUENCE)
                    isString = !isString;

                // if the char is not a part of a string, make it lowercase if it's a letter
                if (!isString)
                    buffer.append((charToAdd < 'A' || charToAdd > 'Z')
                            ? charToAdd
                            : (char) (charToAdd + 0x20));
                else
                    // if the char is a part of a string, just add it as it is
                    buffer.append(charToAdd);
            }

            prevChar = lineToFormat.charAt(i);
        }

        if (isString)
            throw new WrongAssemblyLineException("\nWrong assembly line found:\n"
                    + "'" + lineToFormat + "' contains too many string separators");

        // add the rest found in buffer
        return buffer.length() > 0
                ? lineWithoutWhitespace.append(buffer).toString().trim()
                : lineWithoutWhitespace.toString().trim();
    }

    public static void main(String[] args) throws IOException {
        String path = "/home/hamudi/Developing/Repositories/H-Language/H-Language/IO/NumberPrinterAssembly.hlan";
        CodeFormatter cf = new CodeFormatter(path);
        for (String line : cf.formattedCode)
            System.out.println(line);
    }
}
