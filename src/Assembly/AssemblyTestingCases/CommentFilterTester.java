import static org.junit.Assert.*;

import Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions.WrongAssemblyLineException;
import Assembly.AssemblyExceptions.FunctionalExceptions.WrongCommentFormatException;
import Assembly.AssemblyExceptions.FunctionalExceptions.WrongFilePathException;
import Assembly.CodeFormatter;
import org.junit.Test;

import java.io.IOException;

/***
 * Test-Class to determine whether the input can be correctly read and formatted
 */
public class CommentFilterTester {
    private final String[] singleLinedComments = new String[]{
            "# comment line",
            "var EQU 555 # comment",
            "var EQu#close comment",
            "var EQU 123 # comment {is this a long comment?}",
            "#} MOV a:5 comment with long comment's bracket",
            "# { other bracket found with space",
            "#",
            "{",
            "}",
            "#{}",
            ""
    };

    /**
     * Test if null pointer is handled correctly
     * @throws IOException if unable to open the file to read from
     */
    @SuppressWarnings("ConstantConditions")
    @Test(expected = WrongFilePathException.class)
    public void nullPointersTest() throws IOException {
        new CodeFormatter((String) null);
    }

    /**
     * Test if null pointer is handled correctly
     */
    @Test(expected = WrongCommentFormatException.class)
    public void nullPointerInstructionsTest() {
        new CodeFormatter(new String[]{"", "#", "{", "}", null});
    }

    /**
     * Test a special case of a single-line comment
     */
    @Test
    public void WrongBracketsClosingTestTolerate() {
        CodeFormatter codeFormatter = new CodeFormatter(new String[]{"instr# {comment}}}"});
        assertEquals(codeFormatter.getFormattedCode().get(0), "instr");

        System.out.println(codeFormatter.getFormattedCode().get(0));
    }

    /**
     * Test normal single lined comments
     */
    @Test
    public void singleLineCommentsTests() {
        String expectedOutput = "var EQU 555\n" +
                "var EQu\n" +
                "var EQU 123\n";

        CodeFormatter codeFormatter = new CodeFormatter(singleLinedComments);
        StringBuilder realOutput = new StringBuilder();
        for (String line : codeFormatter.getFormattedCode())
            realOutput.append(line).append("\n");

        assertEquals(expectedOutput.toLowerCase(), realOutput.toString());

        System.out.println(realOutput.toString());
    }

    private final String[] multipleLinedComments = new String[]{
            "{ hello, i'm comment} instr EQU value",
            "{ comment stretching ",
            "within two lines} multiple equ value",
            "begin EQU three { comment",
            "} end EQU four",
            "{comment} instr EQU between {second comment}",
            "{comment} first instr {",
            "second line} second instr {end}third instr {comment",
            "{comment}{second comment}{{thirdComment}{",
            "fourth comment} here EQU last",
            "{ #is not single line } single EQU line",
            "# {single line comment} single_second EQU line",
            "<string> def: \"hello, \\\"{}} world!\"",
            "<test> def: \"hello, }\"",

    };

    /**
     * Test multiple-line comments
     */
    @Test
    public void multipleLineCommentsTest() {
        String expectedOutput = "instr EQU value\n" +
                "multiple equ value\n" +
                "begin EQU three\n" +
                "end EQU four\n" +
                "instr EQU between\n" +
                "first instr\n" +
                "second instr third instr\n" +
                "here EQU last\n" +
                "single EQU line\n" +
                "<string> def: \"hello, \\\"{}} world!\"\n" +
                "<test> def: \"hello, }\"\n";

        CodeFormatter codeFormatter = new CodeFormatter(multipleLinedComments);
        StringBuilder realOutput = new StringBuilder();
        for (String line : codeFormatter.getFormattedCode())
            realOutput.append(line).append("\n");

        assertEquals(expectedOutput.toLowerCase(), realOutput.toString());

        System.out.println(realOutput.toString());
    }

    /**
     * Test a special case of a multiple-line comment
     */
    @Test(expected = WrongCommentFormatException.class)
    public void wrongBracketsClosingTest() {
        new CodeFormatter(new String[]{"{comment}}"});
    }

    private final String[] camelCaseInput = new String[]{
            "\"Hello, World\" Hello WoRlD",
            "<VaR> EQU \"STRI\\\"NG\"",
            "\"\"\"Hello, WorldAZ\"AZ"
    };

    @Test
    public void camelCaseStringTest() {
        String expectedOutput = "\"Hello, World\" hello world\n" +
                "<var> equ \"STRI\\\"NG\"\n" +
                "\"\"\"Hello, WorldAZ\"az\n";

        CodeFormatter codeFormatter = new CodeFormatter(camelCaseInput);
        StringBuilder realOutput = new StringBuilder();
        for (String line : codeFormatter.getFormattedCode())
            realOutput.append(line).append("\n");

        assertEquals(expectedOutput, realOutput.toString());

        System.out.println(realOutput.toString());
    }

    @Test(expected = WrongAssemblyLineException.class)
    public void tooManyStringSeparators() {
        System.out.println(new CodeFormatter(new String[]{"\"Hello, World\" \"  "}).getFormattedCode());
    }
}
