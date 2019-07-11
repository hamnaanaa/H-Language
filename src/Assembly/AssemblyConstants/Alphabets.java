package Assembly.AssemblyConstants;

import java.util.Arrays;

public enum Alphabets {

    /**
     * The binary alphabet { 0, 1 }
     */
    BINARY("01"),

    /**
     * The octal alphabet { 0, 1, 2, 3, 4, 5, 6, 7 }
     */
    OCTAL("01234567"),

    /**
     * The decimal alphabet { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }
     */
    DECIMAL("0123456789"),

    /**
     * The hexadecimal alphabet { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, A, B, C, D, E, F, a, b, c, d, e, f }
     */
    HEXADECIMAL("0123456789ABCDEFabcdef"),

    /**
     * The lowercase alphabet { a, b, c, ..., z }
     */
    LOWERCASE("abcdefghijklmnopqrstuvwxyz"),

    /**
     * The uppercase alphabet { A, B, C, ..., Z }
     */
    UPPERCASE("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),

    SPECIAL_SIGNS("_"),

    /**
     * The base-64 alphabet (64 characters)
     */
    BASE64("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"),

    /**
     * General valid alphabet to use
     */
    VALID_CHARS(LOWERCASE, DECIMAL, SPECIAL_SIGNS);

    private final char[] alphabet;

    private final int alphabetSize;

    public int getAlphabetSize() {
        return alphabetSize;
    }

    public char[] getAlphabet() {
        return alphabet;
    }

    /**
     * Constructor of an alphabet
     *
     * @param alphabet array of chars that represent letters (literals)
     */
    Alphabets(char[] alphabet) {
        if (alphabet == null || alphabet.length == 0)
            throw new IllegalArgumentException("\nThe alphabet must consist of at least one literal");

        this.alphabet = alphabet;
        this.alphabetSize = alphabet.length;
    }

    /**
     * Additional constructor that takes a string and makes an array of characters out of it
     *
     * @param alphabet String representation of the alphabet
     */
    Alphabets(String alphabet) {
        this(alphabet.toCharArray());
    }

    /**
     * Additional constructor that takes multiple alphabets and merges them into one
     *
     * @param alphabets multiple alphabets
     */
    Alphabets(Alphabets... alphabets) {
        int totalSize = 0;
        for (Alphabets alphabet : alphabets)
            totalSize += alphabet.getAlphabetSize();

        char[] charLiterals = new char[totalSize];
        int counter = 0;
        for (Alphabets alphabet : alphabets) {
            for (char letter : alphabet.getAlphabet())
                charLiterals[counter++] = letter;
        }

        this.alphabet = charLiterals;
        this.alphabetSize = totalSize;

    }

    /**
     * Method to determine whether the given character is in the alphabet
     *
     * @param character the character
     * @return true if the character is in the given alphabet
     */
    public boolean contains(char character) {
        for (char literal : alphabet)
            if (literal == character)
                return true;

        return false;
    }

    /**
     * Method to determine the index if the given character
     *
     * @param character the character to find
     * @return the index corresponding to the character or -1 if not found
     */
    public int indexOf(char character) {
        for (int i = 0; i < alphabetSize; i++)
            if (alphabet[i] == character)
                return i;

        return -1;
    }

    /**
     * Returns the character corresponding to the argument index
     *
     * @param index the index of the corresponding character
     * @return the character corresponding to the index
     * @throws IllegalArgumentException if the index is not valid
     */
    public char charOf(int index) throws IllegalArgumentException {
        if (index < 0 || index >= alphabetSize) {
            throw new IllegalArgumentException("index must be between 0 and " + alphabetSize + ": " + index);
        }

        return alphabet[index];
    }

    @Override
    public String toString() {
        return Arrays.toString(alphabet);
    }
}