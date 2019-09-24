import Assembly.AssemblyInstructions.Instruction;
import Assembly.AssemblyInstructions.TEXT_Instruction;
import Assembly.InstructionParser;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class InstructionParserTester {

    private static String TESTS_SEPARATOR = separate(100);

    @SuppressWarnings("SameParameterValue")
    private static String separate(int i) {
        char[] chars = new char[i];
        Arrays.fill(chars, '=');
        return String.valueOf(chars);
    }


    /**
     * Method to test the parsing of valid arity 1 TEXT Instructions
     */
    @Test
    public void arity_1_TEXT_InstructionsTest_1() {
        System.out.println("\n## VALID_ARITY_1_TEXT_INSTRUCTIONS_TEST:");

        String[] validInstructions = new String[]{
                ".. text:",
                "%% entry:",
                "newline",
                "%% second:",
                "nop",
                "%% third:",
                "ifetch"
        };
        InstructionParser parser = new InstructionParser(validInstructions);

        int expectedValidTEXTInstructionsCounter = validInstructions.length - 1;
        int validTEXTInstructionsCounter = 0;

        for (Instruction validInstruction : parser.getInstructions()) {
            System.out.print(!validInstruction.equals(parser.getInstructions().get(parser.getInstructions().size() - 1))
                    ? validInstruction + " :: "
                    : validInstruction + "\n");
            validTEXTInstructionsCounter++;
        }

        assertEquals(expectedValidTEXTInstructionsCounter, validTEXTInstructionsCounter);

        System.out.println("\n\n" + validTEXTInstructionsCounter + " out of " + expectedValidTEXTInstructionsCounter
        + " arity 1 TEXT Instructions were correctly parsed!");
        System.out.println(TESTS_SEPARATOR);
    }


    @Test
    public void entryLabelToInstructionMapTest() {
        // TODO
    }
}