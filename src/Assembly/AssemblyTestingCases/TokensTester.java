import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.AssemblyExceptions.FunctionalExceptions.FunctionalTokenExceptions.WrongAssemblyLineException;
import Assembly.AssemblyExceptions.InstructionParserExceptions.*;
import Assembly.AssemblyFunctionality.AssemblyFunctions;
import Assembly.AssemblyTokens.*;
import Assembly.CodeFormatter;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Test-Class to determine whether all tokens are correctly formatted as expected
 */
@SuppressWarnings("Duplicates")
public class TokensTester {

    private static String TESTS_SEPARATOR = separate(100);

    @SuppressWarnings("SameParameterValue")
    private static String separate(int i) {
        char[] chars = new char[i];
        Arrays.fill(chars, '=');
        return String.valueOf(chars);
    }

    /***
     * Method to test the initialization of a valid registry token
     * (correct registry names and indices as integers)
     */
    @Test
    public void registryAccessTokenTest_1_1() {
        System.out.println("\n## VALID_REGISTRY_ACCESS_TEST:\n(integers)");

        String[] registryNames = {"a", "b"};
        int[] registryIndices = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

        int expectedValidAccessCounter = registryNames.length * registryIndices.length;
        int validAccessCounter = 0;

        for (String registryName : registryNames) {
            System.out.println("\nRegistry '" + registryName + "':");
            for (int registryIndex : registryIndices) {
                System.out.print(registryIndex != registryIndices[registryIndices.length - 1]
                        ? new RegistryAccessToken(registryName, registryIndex).toString() + " :: "
                        : new RegistryAccessToken(registryName, registryIndex).toString() + "\n");

                validAccessCounter++;
            }
        }

        assertEquals(expectedValidAccessCounter, validAccessCounter);

        System.out.println("\n\n" + validAccessCounter + " out of " + expectedValidAccessCounter
                + " registry accesses with integer-indices were successfully initialized!");
        System.out.println(TESTS_SEPARATOR);
    }

    /**
     * Method to test the initialization of a valid registry token
     * (correct registry names in indices as expressions)
     */
    @Test
    public void registryAccessTokenTest_1_2() {
        System.out.println("\n## VALID_REGISTRY_ACCESS_TEST:\n(expressions)");

        String[] expressionsToEvaluate = {
                "0",
                "15",
                "2+3",
                "2 + 3",
                "2+ 3",
                "2 +3",
                "(2 + 3)",
                "(1 +(1 + (1+ 1)))",
                "((15) * 13) % 16",
                "0x5",
                "0x000005",
                // "0b01" after implementing the reverse polish notation
                "0xa + (32 % 15)"
        };
        String defaultRegistryName = "a";

        int expectedValidAccessCounter = expressionsToEvaluate.length;
        int validAccessCounter = 0;

        for (String expression : expressionsToEvaluate) {
            System.out.print(!expression.equals(expressionsToEvaluate[expectedValidAccessCounter - 1])
                    ? new RegistryAccessToken(defaultRegistryName, expression).toString() + " :: "
                    : new RegistryAccessToken(defaultRegistryName, expression).toString() + "\n");

            validAccessCounter++;
        }

        assertEquals(expectedValidAccessCounter, validAccessCounter);

        System.out.println("\n\n" + validAccessCounter + " out of " + expectedValidAccessCounter
                + " registry accesses with expression-indices were successfully initialized!");
        System.out.println(TESTS_SEPARATOR);
    }

    /**
     * Method to test the initialization of a valid registry token
     * (correct input)
     */
    @Test
    public void registryAccessTokenTest_1_3() {
        System.out.println("\n## VALID_REGISTRY_ACCESS_TEST:\n(combined)");

        String[] validRegistryAcesses = new String[]{
                "a:0",
                "b:0",
                "a:15",
                "b:15",
                "a:(12 + 3)",
                "b:(12+3)",
                "a:(15 / 15)",
                "b:(15/15)-1"
        };

        int expectedValidRegistryAccessesCounter = validRegistryAcesses.length;
        int validRegistryAccessesCounter = 0;

        for (String validRegistryAccess : validRegistryAcesses) {
            System.out.print(!validRegistryAccess.equals(validRegistryAcesses[expectedValidRegistryAccessesCounter - 1])
                    ? new RegistryAccessToken(validRegistryAccess).toString() + " :: "
                    : new RegistryAccessToken(validRegistryAccess).toString() + "\n");

            validRegistryAccessesCounter++;
        }

        assertEquals(expectedValidRegistryAccessesCounter, validRegistryAccessesCounter);

        System.out.println("\n\n" + validRegistryAccessesCounter + " out of " + expectedValidRegistryAccessesCounter
                + " registry accesses were successfully initialized!");
        System.out.println(TESTS_SEPARATOR);
    }

    /***
     * Method to test the initialization of a RegistryAccessToken with a wrong registry name
     * (UpperCase is not tested, because CodeFormatter makes sure there is no UpperCase-Input)
     * @see CodeFormatter for the lowercase implementation
     * @see AssemblyConstants for the list of allowed registry names
     */
    @Test
    public void registryAccessTokenTest_2_1() {
        System.out.println("\n## REGISTRY_ACCESS_EXCEPTIONS_TEST:\n(names)");

        String[] nonValidRegistryNames = new String[]{
                "c",
                "d",
                "",
                null,
                "A",
                "RANDOM",
                "aa"
        };
        int validRegistryIndex = 0;

        int expectedExceptionsCounter = nonValidRegistryNames.length;
        int exceptionsCounter = 0;

        for (String nonValidRegistryName : nonValidRegistryNames) {
            try {
                new RegistryAccessToken(nonValidRegistryName, validRegistryIndex);
            } catch (NonValidRegistryAccessException e) {
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
     * Method to test the initialization of an invalid registry access token
     * (wrong registry indices (negative and positive))
     *
     * @see AssemblyConstants for the allowed interval for registry indices
     */
    @Test
    public void registryAccessTokenTest_2_2() {
        System.out.println("\n## REGISTRY_ACCESS_EXCEPTIONS_TEST:\n(indices)");

        int[] nonValidRegistryIndices = {-15, -1, 16, 32456};
        String validRegistryName = "a";

        int expectedExceptionsCounter = nonValidRegistryIndices.length;
        int exceptionsCounter = 0;

        for (int nonValidRegistryIndex : nonValidRegistryIndices) {
            try {
                new RegistryAccessToken(validRegistryName, nonValidRegistryIndex);
            } catch (NonValidRegistryAccessException e) {
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
     * Method to test the initialization of an invalid registry access token
     * (wrong expressions)
     *
     * @see AssemblyFunctions for the implementation details
     */
    @Test
    public void registryAccessTokenTest_2_3() {
        System.out.println("\n## REGISTRY_ACCESS_EXCEPTIONS_TEST:\n(expressions)");

        String[] nonValidExpressions = new String[]{
                null,
                "",
                "a",
                "(",
                ")",
                "()",
                "0o15",
                "((2 + 3)",
                "(2 + 3))"
        };
        String validRegistryName = "a";

        int expectedExceptionsCounter = nonValidExpressions.length;
        int exceptionsCounter = 0;

        for (String nonValidExpression : nonValidExpressions) {
            try {
                new RegistryAccessToken(validRegistryName, nonValidExpression);
            } catch (NonValidRegistryAccessException e) {
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
     * Method to test the initialization of an invalid registry access token
     * (wrong input)
     */
    @Test
    public void registryAccessTokenTest_2_4() {
        System.out.println("\n## REGISTRY_ACCESS_EXCEPTIONS_TEST:\n(combined)");

        String[] nonValidRegistryAccesses = new String[]{
                null,
                "",
                ":",
                "::",
                ":::",
                "a:",
                "b:",
                "c:",
                ":15",
                ":0",
                "a:-1",
                "a:16",
                "a: ",
                "c:0",
                "c:15",
                "a:b:15",
                "a:1:2",
                "a:1:b"
        };

        int expectedExceptionsCounter = nonValidRegistryAccesses.length;
        int exceptionsCounter = 0;

        for (String nonValidRegistryAccess : nonValidRegistryAccesses) {
            try {
                new RegistryAccessToken(nonValidRegistryAccess);
            } catch (NonValidRegistryAccessException e) {
                System.out.println(e.getMessage());
                exceptionsCounter++;
            }
        }

        assertEquals(expectedExceptionsCounter, exceptionsCounter);

        System.out.println("\n\n" + exceptionsCounter + " out of " + expectedExceptionsCounter
                + " exceptions were successfully caught!");
        System.out.println(TESTS_SEPARATOR);
    }

    /**
     * Method to test the initialization of a valid operator token
     */
    @Test
    public void operatorTokenTest_1() {
        System.out.println("\n## VALID_OPERATOR_TEST:");

        String[] ops = new String[]{
                "mov",
                "equ",
                "jmp",
                "printstr"
        };

        String expectedOutput = "mov\n" +
                "equ\n" +
                "jmp\n" +
                "printstr\n";
        StringBuilder realOutput = new StringBuilder();

        int expectedValidOperatorTokensCounter = ops.length;
        int validOperatorTokensCounter = 0;

        for (String op : ops) {
            OperatorToken token = new OperatorToken(op);
            realOutput.append(token.toString()).append("\n");

            System.out.print(!op.equals(ops[expectedValidOperatorTokensCounter - 1])
                    ? token.toString() + " :: "
                    : token.toString() + "\n");

            validOperatorTokensCounter++;
        }

        assertEquals(expectedValidOperatorTokensCounter, validOperatorTokensCounter);
        assertEquals(expectedOutput, realOutput.toString());

        System.out.println("\n\n" + validOperatorTokensCounter + " out of " + expectedValidOperatorTokensCounter
                + " operators were successfully initialized!");
        System.out.println(TESTS_SEPARATOR);
    }

    /**
     * Method to test the initialization of an invalid operator token
     */
    @Test
    public void operatorTokenTest_2() {
        System.out.println("\n## OPERATOR_EXCEPTIONS_TEST:");

        String[] nonValidOPs = new String[]{
                "text",
                "MOV",
                "EqU",
                "var",
                null,
                "",
                "<",
                "!",
                ".",
                "_",
                " "
        };

        int expectedExceptionsCounter = nonValidOPs.length;
        int exceptionsCounter = 0;

        for (String nonValidOP : nonValidOPs) {
            try {
                new OperatorToken(nonValidOP);
            } catch (NonValidOperatorException e) {
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
     * Method to test the initialization of a valid name literal token
     *
     * @see NameLiteralToken for the implementation details
     */
    @Test
    public void nameLiteralTokenTest_1() {
        System.out.println("\n## VALID_NAME_LITERAL_TEST:");

        String[] nameLiterals = new String[]{
                "label",
                "stringer",
                "nameliteral",
                "my_label",
                "_",
                "____",
                "ha123"
        };

        String expectedOutput = "label\n" +
                "stringer\n" +
                "nameliteral\n" +
                "my_label\n" +
                "_\n" +
                "____\n" +
                "ha123\n";
        StringBuilder realOutput = new StringBuilder();

        int expectedValidNameLiteralsCounter = nameLiterals.length;
        int validNameLiteralsCounter = 0;

        for (String nameLiteral : nameLiterals) {
            NameLiteralToken token = new NameLiteralToken(nameLiteral);
            realOutput.append(token.toString()).append("\n");

            System.out.print(!nameLiteral.equals(nameLiterals[expectedValidNameLiteralsCounter - 1])
                    ? token.toString() + " :: "
                    : token.toString() + "\n");

            validNameLiteralsCounter++;
        }

        assertEquals(expectedValidNameLiteralsCounter, validNameLiteralsCounter);
        assertEquals(expectedOutput, realOutput.toString());

        System.out.println("\n\n" + validNameLiteralsCounter + " out of " + expectedValidNameLiteralsCounter
                + " name literals were correctly initialized!");
        System.out.println(TESTS_SEPARATOR);
    }

    /**
     * Method to test the initialization of an invalid name literal token
     */
    @Test
    public void nameLiteralTokenTest_2() {
        System.out.println("\n## NAME_LITERAL_EXCEPTIONS_TEST:");

        String[] nonValidNameLiterals = new String[]{
                "!",
                ".",
                null,
                "",
                "<",
                "1",
                "123",
                "?",
                "|"
        };

        int expectedExceptionsCounter = nonValidNameLiterals.length;
        int exceptionsCounter = 0;

        for (String nonValidNameLiteral : nonValidNameLiterals) {
            try {
                new NameLiteralToken(nonValidNameLiteral);
            } catch (NonValidNameLiteralException e) {
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
     * Method to test the initialization of a valid name literal with index token
     */
    @Test
    public void nameLiteralWithIndexTokenTest_1() {
        System.out.println("\n## VALID_NAME_LITERAL_WITH_INDEX_TEST:");

        String[] validNameLiteralsWithIndex = new String[]{
                "var[15]",
                "array[2]",
                "text[(15 + 3)]",
                "i_dont_know[2*2]",
                "hahahhaha[0]"
        };

        int expectedValidNameLiteralsWithIndexCounter = validNameLiteralsWithIndex.length;
        int validNameLiteralsWithIndexCounter = 0;

        for (String validNameLiteralWithIndex : validNameLiteralsWithIndex) {
            NameLiteralWithIndexToken token = new NameLiteralWithIndexToken(validNameLiteralWithIndex);

            System.out.print(!validNameLiteralWithIndex.equals(validNameLiteralsWithIndex[expectedValidNameLiteralsWithIndexCounter - 1])
                    ? token.toString() + " :: "
                    : token.toString() + "\n");

            validNameLiteralsWithIndexCounter++;
        }

        assertEquals(expectedValidNameLiteralsWithIndexCounter, validNameLiteralsWithIndexCounter);

        System.out.println("\n\n" + validNameLiteralsWithIndexCounter + " out of " +
                expectedValidNameLiteralsWithIndexCounter + " name literals with index were correctly initialized!");
        System.out.println(TESTS_SEPARATOR);
    }

    /**
     * Method to test the initialization of an invalid name literal with index token
     */
    @Test
    public void nameLiteralWithIndexTokenTest_2() {
        System.out.println("\n## NAME_LITERAL_WITH_INDEX_EXCEPTIONS_TEST:");

        String[] nonValidNameLiteralsWithIndex = new String[]{
                null,
                "",
                ")",
                "[",
                "]",
                "[]",
                "[[",
                "[2]",
                "<",
                ">",
                "<>",
                "<>[",
                "<>]",
                "<>[]",
                "v a[1]",
                "var[-1]",
                "var[2*(-1)]",
                "<var>[5]",
                "va!r[5]",
                "var[]",
                "var[1.3]",
                "var[1,3]"
        };

        int expectedExceptionsCounter = nonValidNameLiteralsWithIndex.length;
        int exceptionsCounter = 0;

        for (String nonValidNameLiteralWithIndex : nonValidNameLiteralsWithIndex) {
            try {
                if (nonValidNameLiteralWithIndex != null && nonValidNameLiteralWithIndex.equals("var[1,3]"))
                    System.out.println("HHH");
                new NameLiteralWithIndexToken(nonValidNameLiteralWithIndex);
            } catch (NonValidNameLiteralException e) {
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
     * Method to test the initialization of a valid access label token
     */
    @Test
    public void accessLabelTokenTest_1() {
        System.out.println("\n## VALID_ACCESS_LABEL_TEST:");

        String[] accessLabels = new String[]{
                "<pointer>",
                "<test>",
                "<___>",
                "<label_123>",
                "<to_ke_ni_ze>"
        };

        String expectedOutput = "pointer\n" +
                "test\n" +
                "___\n" +
                "label_123\n" +
                "to_ke_ni_ze\n";
        StringBuilder realOutput = new StringBuilder();

        int expectedValidAccessLabelsCounter = accessLabels.length;
        int validAccessLabelsCounter = 0;

        for (String accessLabel : accessLabels) {
            AccessLabelToken token = new AccessLabelToken(accessLabel);
            realOutput.append(token.toString()).append("\n");

            System.out.print(!accessLabel.equals(accessLabels[expectedValidAccessLabelsCounter - 1])
                    ? token.toString() + " :: "
                    : token.toString() + "\n");

            validAccessLabelsCounter++;
        }

        assertEquals(expectedValidAccessLabelsCounter, validAccessLabelsCounter);
        assertEquals(expectedOutput, realOutput.toString());

        System.out.println("\n\n" + validAccessLabelsCounter + " out of " + expectedValidAccessLabelsCounter
                + " access labels were correctly initialized!");
        System.out.println(TESTS_SEPARATOR);
    }

    /**
     * Method to test the initialization of an invalid access label token
     */
    @Test
    public void accessLabelTokenTest_2() {
        System.out.println("\n## ACCESS_LABEL_EXCEPTIONS_TEST:");

        String[] nonValidAccessLabels = new String[]{
                "",
                "<",
                ">",
                "<>",
                null,
                "<<>",
                "label",
                "<l",
                "l>",
                "l",
                ".",
                "",
                "...",
                "!",
                "<!>",
                "<123>",
                "<label> ",
                "<label> text"
        };

        int expectedExceptionsCounter = nonValidAccessLabels.length;
        int exceptionsCounter = 0;

        for (String nonValidAccessLabel : nonValidAccessLabels) {
            try {
                new AccessLabelToken(nonValidAccessLabel);
            } catch (NonValidAccessLabelException e) {
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
     * Method to test the initialization of a valid access label with index token
     */
    @Test
    public void accessLabelWithIndexTokenTest_1() {
        System.out.println("\n## VALID_ACCESS_LABEL_WITH_INDEX_TEST:");

        String[] validAccessLabelsWithIndex = new String[]{
                "<label>[15]",
                "<test>[0]",
                "<label_with_spaces>[(12 + 23)]",
                "<label>[ (12+23) / 5 ]"
        };

        int expectedValidAccessLabelsWithIndexCounter = validAccessLabelsWithIndex.length;
        int validAccessLabelsWithIndexCounter = 0;

        for (String validAccessLabelWithIndex : validAccessLabelsWithIndex) {
            AccessLabelWithIndexToken token = new AccessLabelWithIndexToken(validAccessLabelWithIndex);

            System.out.print(!validAccessLabelWithIndex
                    .equals(validAccessLabelsWithIndex[expectedValidAccessLabelsWithIndexCounter - 1])
                    ? token.toString() + " :: "
                    : token.toString() + "\n");

            validAccessLabelsWithIndexCounter++;
        }

        assertEquals(expectedValidAccessLabelsWithIndexCounter, validAccessLabelsWithIndexCounter);

        System.out.println("\n\n" + validAccessLabelsWithIndexCounter + " out of "
                + expectedValidAccessLabelsWithIndexCounter + " access labels with index were correctly initialized!");
        System.out.println(TESTS_SEPARATOR);
    }

    /**
     * Method to test the initialization of an invalid access label with index token
     */
    @Test
    public void accessLabelWithIndexTokenTest_2() {
        System.out.println("\n## ACCESS_LABEL_WITH_INDEX_EXCEPTIONS_TEST:");

        String[] nonValidAccessLabelsWithIndex = new String[]{
                null,
                "",
                ")",
                "[",
                "]",
                "[]",
                "[[",
                "[2]",
                "<",
                ">",
                "<>",
                "<>[",
                "<>]",
                "<>[]",
                "<label>[]",
                "<>[1]",
                "<label>[1][",
                "<label>[1]]",
                "<la[bel>[1]]",
                "<la[bel>]",
                "<<label>[1]",
                "label[1]",
                "label[1",
                "<label>[1",
                "<label>[2-3]"
        };

        int expectedExceptionsCounter = nonValidAccessLabelsWithIndex.length;
        int exceptionsCounter = 0;

        for (String nonValidAccessLabelWithIndex : nonValidAccessLabelsWithIndex) {
            try {
                new AccessLabelWithIndexToken(nonValidAccessLabelWithIndex);
            } catch (NonValidAccessLabelException e) {
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
     * Method to test the initialization of a valid integer expression token
     */
    @Test
    public void integerExpressionTokenTest_1() {
        System.out.println("\n## VALID_INTEGER_EXPRESSION_TEST:");

        String[] validExpressions = new String[]{
                "15",
                "12 + 13",
                "(12)",
                "((12))",
                "13 % 2",
                "20 / 2",
                "10_000"
        };
        String expectedOutput = "15\n25\n12\n12\n1\n10\n10000\n";
        StringBuilder realOutput = new StringBuilder();

        int expectedValidIntegerExpressionsCounter = validExpressions.length;
        int validIntegerExpressionsCounter = 0;

        for (String validExpression : validExpressions) {
            IntegerExpressionToken token = new IntegerExpressionToken(validExpression);
            realOutput.append(token.toString()).append("\n");

            System.out.print(!validExpression.equals(validExpressions[expectedValidIntegerExpressionsCounter - 1])
                    ? token.toString() + " :: "
                    : token.toString() + "\n");
            validIntegerExpressionsCounter++;
        }

        assertEquals(expectedValidIntegerExpressionsCounter, validIntegerExpressionsCounter);
        assertEquals(expectedOutput, realOutput.toString());

        System.out.println("\n\n" + validIntegerExpressionsCounter + " out of " + expectedValidIntegerExpressionsCounter
                + " integer expressions were correctly initialized!");
        System.out.println(TESTS_SEPARATOR);
    }

    /**
     * Method to test the initialization of an invalid integer expression token
     */
    @Test
    public void integerExpressionTokenTest_2() {
        System.out.println("\n## INTEGER_EXPRESSION_EXCEPTIONS_TEST:");

        String[] nonValidIntegerExpressions = new String[]{
                null,
                "",
                ")",
                "(",
                "()",
                "(1",
                "1 ++ 2",
                "1 + 2 +",
                String.valueOf(AssemblyConstants.SGN_INTEGER_MAX_VALUE + 1),
                String.valueOf(AssemblyConstants.SGN_INTEGER_MIN_VALUE - 1),
        };

        int expectedExceptionsCounter = nonValidIntegerExpressions.length;
        int exceptionsCounter = 0;

        for (String nonValidIntegerExpression : nonValidIntegerExpressions) {
            try {
                new IntegerExpressionToken(nonValidIntegerExpression);
            } catch (NonValidExpressionException e) {
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
     * Method to test the initialization of a valid jump label token
     */
    @Test
    public void jumpLabelTokenTest_1() {
        System.out.println("\n## VALID_JUMP_LABEL_TEST:");

        String[] validJumpLabels = new String[]{
                "%label:",
                "%token:",
                "%h1:",
                "%_:",
                "%__:",
                "%space_label:"
        };

        int expectedValidJumpLabelsCounter = validJumpLabels.length;
        int validJumpLabelsCounter = 0;

        for (String validJumpLabel : validJumpLabels) {
            JumpLabelToken token = new JumpLabelToken(validJumpLabel);

            System.out.print(!validJumpLabel.equals(validJumpLabels[expectedValidJumpLabelsCounter - 1])
                    ? token.toString() + " :: "
                    : token.toString() + "\n");

            validJumpLabelsCounter++;
        }

        assertEquals(expectedValidJumpLabelsCounter, validJumpLabelsCounter);

        System.out.println("\n\n" + validJumpLabelsCounter + " out of " + expectedValidJumpLabelsCounter
                + " jump labels were correctly initialized!");
        System.out.println(TESTS_SEPARATOR);
    }

    /**
     * Method to test the initialization of an invalid jump label token
     */
    @Test
    public void jumpLabelTokenTest_2() {
        System.out.println("\n## JUMP_LABEL_EXCEPTIONS_TEST:");

        String[] nonValidJumpLabels = new String[]{
                null,
                "",
                ")",
                "_",
                "<",
                "%",
                ":",
                "%:",
                "%la ble:",
                "%<:",
                "%la<bel:",
                "%la[bel:",
                "%la]bel:",
                "%>:",
                "%la>bel:",
                "% label:",
                "%label :",
                "%label: ",
                "%label: text"
        };

        int expectedExceptionsCounter = nonValidJumpLabels.length;
        int exceptionsCounter = 0;

        for (String nonValidJumpLabel : nonValidJumpLabels) {
            try {
                new JumpLabelToken(nonValidJumpLabel);
            } catch (NonValidJumpLabelException e) {
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
     * Method to test the initialization of a valid entry label token
     */
    @Test
    public void entryLabelTokenTest_1() {
        System.out.println("\n## VALID_ENTRY_LABEL_TEST:");

        String[] validEntryLabels = new String[]{
                "%% label:",
                "%% label_with_space:",
                "%% label5_12_3:"
        };

        int expectedValidEntryLabelsCounter = validEntryLabels.length;
        int validEntryLabelsCounter = 0;

        for (String validEntryLabel : validEntryLabels) {
            EntryLabelToken token = new EntryLabelToken(validEntryLabel);

            System.out.print(!validEntryLabel.equals(validEntryLabels[expectedValidEntryLabelsCounter - 1])
                    ? token.toString() + " :: "
                    : token.toString() + "\n");

            validEntryLabelsCounter++;
        }

        assertEquals(expectedValidEntryLabelsCounter, validEntryLabelsCounter);

        System.out.println("\n\n" + validEntryLabelsCounter + " out of " + expectedValidEntryLabelsCounter
                + " entry labels were correctly initialized!");
        System.out.println(TESTS_SEPARATOR);
    }

    /**
     * Method to test the initialization of an invalid entry label token
     */
    @Test
    public void entryLabelTokenTest_2() {
        System.out.println("\n## ENTRY_LABEL_EXCEPTIONS_TEST:");

        String[] nonValidEntryLabels = new String[]{
                null,
                "",
                "%",
                ":",
                "%%",
                "%%:",
                "%% :",
                "%%label:",
                "%% label space:",
                "%% 1label:",
                "%% label",
                "% label:",
                "label:",
                "%%% label:",
                "%% label:miv",
                "%% label:miv:"
        };

        int expectedExceptionsCounter = nonValidEntryLabels.length;
        int exceptionsCounter = 0;

        for (String nonValidEntryLabel : nonValidEntryLabels) {
            try {
                new EntryLabelToken(nonValidEntryLabel);
            } catch (NonValidEntryLabelException e) {
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
     * Method to test the initialization of a valid string literal token
     */
    @Test
    public void stringLiteralTokenTest_1() {
        System.out.println("\n## VALID_STRING_LITERAL_TEST:");

        String[] validStringLiterals = new String[]{
                "\"hello, world\"",
                "\"Hello, World\"",
                "\"\"",                             // ""
                "\" \"",                            // " "
                "\"  \"",                           // "  "
                "\"_\"",                            // "_"
                "\" spaces every where \"",
                "\" \\\" \"",                       // " " "
                "\"\\\"\"",                         // """
                "\"13\"",                           // "13"
                "'\\\"'",                           // """
                "'\\ '",                            // " "
                "\'c\'",                            // "c"
                "\"Hello ''''''\\\"''' World!\"",   // "Hello ''''''"''' World!"
                "\"\\\\\"",                         // "\"
                "\"\\\\\\\"\"",                     // "\""
                "\"esc\\caped\"",                   // "esccaped"
                "\"'char'and''char''''''\\\"\"",    // "'char'and'char''''''""
                "\"[][][(][][][))]\""
        };

        int expectedValidStringLiteralsCounter = validStringLiterals.length;
        int validStringLiteralsCounter = 0;

        for (String validStringLiteral : validStringLiterals) {
            StringLiteralToken token = new StringLiteralToken(validStringLiteral);

            System.out.print(!validStringLiteral.equals(validStringLiterals[expectedValidStringLiteralsCounter - 1])
                    ? token.toString() + " :: "
                    : token.toString() + "\n");

            validStringLiteralsCounter++;
        }

        assertEquals(expectedValidStringLiteralsCounter, validStringLiteralsCounter);

        System.out.println("\n\n" + validStringLiteralsCounter + " out of " + expectedValidStringLiteralsCounter
                + " string literals were correctly initialized!");
        System.out.println(TESTS_SEPARATOR);
    }

    /**
     * Method to test the initialization of an invalid string literal token
     */
    @Test
    public void stringLiteralTokenTest_2() {
        System.out.println("\n## STRING_LITERAL_EXCEPTIONS_TEST:");

        String[] nonValidStringLiterals = new String[]{
                null,
                "",
                "\\",
                "\"",
                " \"",
                "\"string",
                "string\"",
                "\"string\"er\"",
                "\"string\"er",
                "\"stringer\\\"",
                "'chars",
                "chars'",
                "'chars'",
                "\'\\'"
        };

        int expectedExceptionsCounter = nonValidStringLiterals.length;
        int exceptionsCounter = 0;

        for (String nonValidStringLiteral : nonValidStringLiterals) {
            try {
                new StringLiteralToken(nonValidStringLiteral);
            } catch (NonValidStringLiteralException e) {
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
     * Method to test the initialization of a valid array token
     */
    @Test
    public void arrayTokenTest_1() {
        System.out.println("\n## VALID_ARRAY_TEST:");

        String[] validArrays = new String[]{
                "[]",                                   // empty array
                "[ ]",                                  // empty array with whitespace
                "[1]",                                  // single value
                "[0, 1, 2, 3, 4, 5]",                   // normal numbers
                "[-5, -4, -3, -2, -1]",                 // negative numbers
                "[15, (15), ((15)), (((15)))]",         // redundant parenthesis
                "[-15, -(15), -(-(-15)), -((-(-15)))]", // redundant parenthesis with negative numbers
                "[0,1,2,3,4,5]",                        // no spaces
                "[-5,-4,-3,-2,-1]",                     // no spaces negative numbers
                "[0, 1,2, 3, 4,5,  6]",                 // redundant (different) whitespaces
                "[\"\", \" \", \"_\", \"15\"]",         // string array
                "[[1,2,3], [4,5,6], [7,8,9]]",          // complex array
                "[\"Hello\", 'c']",                     // string and char array
                "[\"Mixed\", 15, [1,2,3]]",             // complex mixed array
                "[one, two, three]"                     // varable labels
        };

        int expectedValidArraysCounter = validArrays.length;
        int validArraysCounter = 0;

        for (String validArray : validArrays) {
            ArrayToken token = new ArrayToken(validArray);

            System.out.print(!validArray.equals(validArrays[expectedValidArraysCounter - 1])
                    ? token.toString() + " :: "
                    : token.toString() + "\n");

            validArraysCounter++;
        }

        assertEquals(expectedValidArraysCounter, validArraysCounter);

        System.out.println("\n\n" + validArraysCounter + " out of " + expectedValidArraysCounter
                + " string literals were correctly initialized!");
        System.out.println(TESTS_SEPARATOR);
    }

    /**
     * Method to test the initialization of an invalid array token
     */
    @Test
    public void arrayTokenTest_2() {
        System.out.println("\n## ARRAY_EXCEPTIONS_TEST:");

        String[] nonValidArrays = new String[]{
                null,
                "",
                " ",
                "[",
                "]",
                "[<array>]",                    // accessLabelToken
                "[<array>[1]]",                 // accessLabelWithIndexToken
                "[%% label:]",                  // entryLabelToken
                "[%label:]",                    // jumpLabelToken
                "[mov]",                        // operatorToken
                "[a:5]",                        // registryAccessToken
                "[[15, 23], 22, [mov, %jmp:]]", // complex array with operator and jumpLabel tokens
                "[1 2 3]",                      // forgotten commas // TODO : this array is checked as a normal 3 digit number
        };

        int expectedExceptionsCounter = nonValidArrays.length;
        int exceptionsCounter = 0;

        for (String nonValidArray : nonValidArrays) {
            try {
                new ArrayToken(nonValidArray);
            } catch (NonValidArrayException | WrongAssemblyLineException e) {
                System.out.println(e.getMessage());
                exceptionsCounter++;
            }
        }

        assertEquals(expectedExceptionsCounter, exceptionsCounter);

        System.out.println("\n\n" + exceptionsCounter + " out of " + expectedExceptionsCounter
                + " exceptions were caught!");
        System.out.println(TESTS_SEPARATOR);
    }
}


