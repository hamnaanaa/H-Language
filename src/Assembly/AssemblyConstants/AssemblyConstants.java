package Assembly.AssemblyConstants;

import Assembly.Preprocessor;
import Assembly.CodeFormatter;
import Assembly.AssemblyFunctionality.AssemblyFunctions;
import Assembly.AssemblyTokens.RegistryAccessToken;

import java.util.Arrays;

// @formatter:off
public class AssemblyConstants {
    /**
     * Architecture of H-Language Computer
     */
    public static final byte BIT_ARCHITECTURE = 16;

    /**
     * Reserved chars for separators
     * @see CodeFormatter
     */
    public static final char SINGLE_LINE_COMMENT = '#';
    public static final char LONG_COMMENT_OPEN = '{';
    public static final char LONG_COMMENT_CLOSE = '}';
    public static final char ESCAPE_SEQUENCE = '\\';
    public static final char STRING_SEPARATOR = '"';
    public static final char CHAR_SEPARATOR = '\'';
    public static final char REGISTRY_ACCESS_SEPARATOR = ':';
    public static boolean isWhitespace(char character) {
        return Character.isWhitespace(character);
    }

    /**
     * Section separators
     * @see Preprocessor
     */
    public static final String SECTION_SEPARATOR = "..";
    public static final String INCLUDE_SEPARATOR = "$$";

    /**
     * Flags for different sections
     * @see Preprocessor
     */
    public static final int INCLUDE_FLAG = 0;
    public static final String INCLUDE_HEADER = INCLUDE_SEPARATOR + " " + "include:";

    public static final int BSS_FLAG = 1;
    public static final String BSS_HEADER = SECTION_SEPARATOR + " " + "bss:";

    public static final int DATA_FLAG = 2;
    public static final String DATA_HEADER = SECTION_SEPARATOR + " " + "data:";

    public static final int VAR_FLAG = 3;
    public static final String VAR_HEADER = SECTION_SEPARATOR + " " + "variables:";

    public static final int TEXT_FLAG = 4;
    public static final String TEXT_HEADER = SECTION_SEPARATOR + " " + "text:";

    /**
     * Available sections for Assembly code
     */
    private static final String[] AVAILABLE_SECTIONS = new String[]{
            INCLUDE_HEADER,
            BSS_HEADER,
            DATA_HEADER,
            VAR_HEADER,
            TEXT_HEADER
    };
    public static String getAvailableSections() {
        return Arrays.toString(AVAILABLE_SECTIONS);
    }

    /**
     * List of valid registry names
     * @see RegistryAccessToken
     * @see AssemblyFunctions
     */
    public static final String[] VALID_REGISTRY_NAMES = new String[]{"a", "b"};
    public static String getValidRegistryNames() {
        return Arrays.toString(VALID_REGISTRY_NAMES);
    }

    /**
     * Interval of valid registry indices
     * @see RegistryAccessToken
     * @see AssemblyFunctions
     */
    public static final int MIN_REGISTRY_INDEX = 0;
    public static final int MAX_REGISTRY_INDEX = 15;
    public static String getValidRegistryIndices() {
        return "[" + MIN_REGISTRY_INDEX + ".." + MAX_REGISTRY_INDEX + "]";
    }

    /**
     * Special separators for each Assembly instruction
     * @see Preprocessor
     */
    public static final String INCLUDE_STATEMENT_SEPARATOR = "::";
    public static final String ENTRY_LABEL_SEPARATOR_OPEN = "%%";
    public static final char ENTRY_LABEL_SEPARATOR_CLOSE = ':';
    public static final char ACCESS_SEPARATOR_OPEN = '<';
    public static final char ACCESS_SEPARATOR_CLOSE = '>';
    public static final char INDEX_SEPARATOR_OPEN = '[';
    public static final char INDEX_SEPARATOR_CLOSE = ']';
    public static final char JUMP_LABEL_SEPARATOR_OPEN = '%';
    public static final char JUMP_LABEL_SEPARATOR_CLOSE = ':';


    /**
     * Defined alphabets to use for labels and variables
     */
    public static final Alphabets VALID_CHARS = Alphabets.VALID_CHARS;
    public static final Alphabets DECIMAL = Alphabets.DECIMAL;

    /**
     * Maximal values for numerical expressions
     */
    public static final int SGN_INTEGER_MAX_VALUE = (int) Math.pow(2, BIT_ARCHITECTURE - 1) - 1;
    public static final int SGN_INTEGER_MIN_VALUE = (-1) * (int) Math.pow(2, BIT_ARCHITECTURE - 1);
}
// @formatter:on
