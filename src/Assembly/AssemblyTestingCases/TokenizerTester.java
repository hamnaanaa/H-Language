import Assembly.AssemblyExceptions.FunctionalExceptions.WrongAssemblyLineException;
import Assembly.AssemblyFunctionality.AssemblyFunctions;
import Assembly.AssemblyTokens.Token;
import Assembly.CodeFormatter;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Test-Class to determine whether all tokens are correctly extracted with a Tokenizer from H-Language programs
 *
 * @see AssemblyFunctions for the implementation details of the Tokenizer
 */
@SuppressWarnings("Duplicates")
public class TokenizerTester {

    private static String TESTS_SEPARATOR = separate(100);
    private static String FILE_PATH = "/home/hamudi/Developing/Repositories/H-Language/H-Language/IO/NumberPrinterAssembly.hlan";

    @SuppressWarnings("SameParameterValue")
    private static String separate(int i) {
        char[] chars = new char[i];
        Arrays.fill(chars, '=');
        return String.valueOf(chars);
    }

    /**
     * Method to test the extraction of all tokens from all lines of a typical H-Language program as Strings
     *
     * @throws IOException if was not able to read the program file
     */
    @Test
    public void allLines_StringTest() throws IOException {
        System.out.println("\n## ALL_LINES_TEST:");

        String path = FILE_PATH;
        CodeFormatter cf = new CodeFormatter(path);
        ArrayList<String> formattedCode = cf.getFormattedCode();

        for (String line : formattedCode) {
            String[] lineTokens = AssemblyFunctions.extractTokens(line);
            System.out.println(Arrays.toString(lineTokens) + " TOKENS EXTRACTED: " + lineTokens.length);
        }
    }

    /**
     * Method to test the extraction of invalid (unclosed) tokens
     */
    @Test
    public void unclosedTokens_StringTest() {
        System.out.println("\n## UNCLOSED_TOKENS_TEST:");

        String[] wrongLines = new String[]{
                "<array def 15",                    //  label separator close is missing
                "array> def 15",                    //  label separator open is missing
                "equ var (15+ 16))",                //  too many parenthesis close
                "equ var ((15+16)",                 //  too many parenthesis open
                "<array> def \"hello world\\\"",    //  escaped string separator close
                "<array> def [5 + 13, \"Hello World\", 'a', ((12 + 13) * 2)",     // array was not closed
                "(15 + 12) * 3)",                   // too many parenthesis open
                "\"string'",                        // different string separators used
                "'string\""                         // different string separators used
        };

        int expectedExceptionsCounter = wrongLines.length;
        int exceptionsCounter = 0;

        for (String wrongLine : wrongLines) {
            try {
                String[] tokens = AssemblyFunctions.extractTokens(wrongLine);
                System.out.println("\n" + Arrays.toString(tokens)
                        + " TOKENS EXTRACTED: " + tokens.length);
            } catch (WrongAssemblyLineException e) {
                System.out.println(e.getMessage());
                exceptionsCounter++;
            }
        }

        assertEquals(expectedExceptionsCounter, exceptionsCounter);

        System.out.println("\n\n" + exceptionsCounter + " out of " + expectedExceptionsCounter
                + " exceptions were caught!");
        System.out.println(TESTS_SEPARATOR);
    }

    /**
     * Method to test the correct extraction of special cases (separators in strings)
     */
    @Test
    public void ignoredSeparators_StringTest() {
        System.out.println("\n## SEPARATORS_IN_STRINGS_TEST:");

        String[] correctLines = new String[]{
                "<array> def \"<<<<>>>>><!>><<><\"",            //  3 tokens
                "<label> def \"(()()()()()((())))))\"",         //  3 tokens
                "<label> def \"[[][][]]][[][\"",                //  3 tokens
                "<label> def \"\\\"\\\"\\\"\\\"\"",             //  3 tokens
                "<label> def \"'''\" a:5",                      //  4 tokens
                "<label> def '\\\"' a:5",                       //  4 tokens
                "<array> def [5 + 13, \"Hello World\", 'a', ((12 + 13) * 2)]"     //  3 tokens
        };

        int expectedCorrectLinesCounter = correctLines.length;
        int correctLinesCounter = 0;

        for (String correctLine : correctLines) {
            String[] tokens = AssemblyFunctions.extractTokens(correctLine);
            System.out.println(Arrays.toString(tokens) + " TOKENS EXTRACTED: " + tokens.length);

            correctLinesCounter++;
        }

        assertEquals(expectedCorrectLinesCounter, correctLinesCounter);

        System.out.println("\n\n" + correctLinesCounter + " out of " + expectedCorrectLinesCounter
                + " lines were correctly tokenized!");
        System.out.println(TESTS_SEPARATOR);
    }

    /**
     * Method to test the extraction of tokens containing spaces
     */
    @Test
    public void spacesInTokens_StringTest() {
        System.out.println("\n## SPACES_IN_TOKENS_TEST:");

        String[] correctOneTokenLines = new String[]{
                "<array with longer name>",         // spaces in array label
                "[5 + 13, \"Hello World\", 'a', ((12 + 13) * 2)]",  // complex array
                "%% section:",                      // section
                "%jmp section:",                    // jump label
                "\"String with spaces \"",          // string with spaces
                "' '",                              // space as a char token
                "((15 + 23) * 12)"                  // expression
        };

        int expectedCorrectOneTokenLinesCounter = correctOneTokenLines.length;
        int correctOneTokenLinesCounter = 0;

        for (String correctOneTokenLine : correctOneTokenLines) {
            String[] tokens = AssemblyFunctions.extractTokens(correctOneTokenLine);
            System.out.println(Arrays.toString(tokens) + " TOKENS EXTRACTED: " + tokens.length);

            if (tokens.length == 1)
                correctOneTokenLinesCounter++;
        }

        assertEquals(expectedCorrectOneTokenLinesCounter, correctOneTokenLinesCounter);

        System.out.println("\n\n" + correctOneTokenLinesCounter + " out of " + expectedCorrectOneTokenLinesCounter
                + " lines were correctly tokenized!");
        System.out.println(TESTS_SEPARATOR);
    }

    /**
     * Method to test the extraction of all tokens from all lines of a typical H-Language program as Tokens
     *
     * @throws IOException if was not able to read the program file
     */
    @Test
    public void allTokensParser_TokenTest() throws IOException {
        System.out.println("\n## ALL_TOKENS_PARSER_TEST:");

        String path = FILE_PATH;
        CodeFormatter cf = new CodeFormatter(path);
        ArrayList<String> formattedCode = cf.getFormattedCode();
        StringBuilder builder = new StringBuilder();

        for (String line : formattedCode) {
            try {
                Token<?>[] lineTokens = AssemblyFunctions.tokenize(line);
                System.out.println("TOKENS EXTRACTED: " + lineTokens.length);
                for (Token token : lineTokens) {
                    String className = token.getClass().toString();
                    System.out.println("- CLASS: " + className.substring(className.lastIndexOf('.')) + " < " + token + " >");
                }
                System.out.println();
            } catch (RuntimeException e) {
                builder.append("\nException happened in line '").append(line).append("'").append(e.getMessage()).append("\n");
            }
        }

        System.out.println("### EXCEPTED:\n" + builder);
    }
}
