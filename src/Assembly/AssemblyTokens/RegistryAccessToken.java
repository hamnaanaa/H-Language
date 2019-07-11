package Assembly.AssemblyTokens;

import Assembly.AssemblyExceptions.FunctionalExceptions.WrongExpressionException;
import Assembly.AssemblyExceptions.FunctionalExceptions.WrongRegistryIndexException;
import Assembly.AssemblyExceptions.FunctionalExceptions.WrongRegistryNameException;
import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidExpressionException;
import Assembly.AssemblyExceptions.PreprocessorExceptions.NonValidRegistryAccessException;
import Assembly.AssemblyFunctionality.AssemblyFunctions;
import Assembly.AssemblyConstants.AssemblyConstants;
import Assembly.AssemblyFunctionality.Pair;
import Assembly.Preprocessor;

import java.util.Arrays;

/**
 * Class that represents a registry access token in H-Language assembly instructions
 *
 * @format registryName:registryIndex
 * @see AssemblyFunctions for the implementation details
 * @see AssemblyConstants for the list of valid registry names and indices
 * @see // TODO : link to all instruction-classes that use registry access token
 * @see Preprocessor
 */
public class RegistryAccessToken extends Token<Pair<String, Integer>> {

    public String getRegistryName() {
        return value.getKey();
    }

    public int getRegistryIndex() {
        return value.getValue();
    }

    /**
     * Constructor for a registry access token that takes a registry access string and parses it into a pair
     *
     * @param registryAccess registry access to parse
     * @throws NonValidRegistryAccessException if the given registry access is not valid
     */
    public RegistryAccessToken(String registryAccess) throws NonValidRegistryAccessException {
        try {
            this.value = handleRegistryAccess(registryAccess);
        } catch (NonValidRegistryAccessException e) {
            throw new NonValidRegistryAccessException("\nNon-valid registry access: '" + registryAccess + "'"
                    + e.getMessage());
        }
    }

    /**
     * Constructor for a registry access token
     * Checks whether the given registry name and index are valid and sets the value to a pair of these with length 1
     *
     * @param registryName  registry name to check
     * @param registryIndex registry index (integer) to check
     * @throws NonValidRegistryAccessException if registry name or index are not valid
     */
    public RegistryAccessToken(String registryName, int registryIndex) throws NonValidRegistryAccessException {
        try {
            this.value = new Pair<>(handleRegistryName(registryName), handleRegistryIndex(registryIndex));
        } catch (WrongRegistryNameException | WrongRegistryIndexException e) {
            throw new NonValidRegistryAccessException("\nNon-valid registry access: '"
                    + registryName + AssemblyConstants.REGISTRY_ACCESS_SEPARATOR + registryIndex + "'" + e.getMessage());
        }
    }

    /**
     * Constructor for a registry access token, that takes String expressions as index values
     * Checks whether the given registry name and index are valid and sets the value to a pair of these
     *
     * @param registryName  String that represents a registry name
     * @param registryIndex String that represents an expression that should be evaluated as a registry index
     * @throws NonValidRegistryAccessException if registry name or index are not valid or if registryIndex-expression
     *                                         cannot be evaluated as String
     */
    public RegistryAccessToken(String registryName, String registryIndex) throws NonValidRegistryAccessException {
        try {
            this.value = new Pair<>(handleRegistryName(registryName), handleRegistryIndex(registryIndex));
        } catch (WrongRegistryNameException | WrongRegistryIndexException | NonValidExpressionException e) {
            throw new NonValidRegistryAccessException("\nNon-valid registry access: '"
                    + registryName + AssemblyConstants.REGISTRY_ACCESS_SEPARATOR + registryIndex + "'" + e.getMessage());
        }
    }

    /**
     * Method to check the given registry access
     *
     * @param registryAccess registry access to check
     * @return pair of string and integer if the given registry access is valid
     * @throws NonValidRegistryAccessException if non-valid registry access found
     */
    private Pair<String, Integer> handleRegistryAccess(String registryAccess) throws NonValidRegistryAccessException {
        if (registryAccess == null || registryAccess.equals(""))
            throw new NonValidRegistryAccessException("\nNull-pointer registry access found");

        String[] tokens = registryAccess.split(String.valueOf(AssemblyConstants.REGISTRY_ACCESS_SEPARATOR));
        if (tokens.length != 2)
            throw new NonValidRegistryAccessException("\nRegistry access must consist of two tokens. Tokens found:\n"
                    + Arrays.toString(tokens));

        return new Pair<>(handleRegistryName(tokens[0]), handleRegistryIndex(tokens[1]));

    }

    /**
     * Method to check the given registry name
     *
     * @param registryName String that represents a registry name to check
     * @return registry name if valid or null (not reachable due to exceptions)
     * @throws WrongRegistryNameException if the given registry name is not valid
     * @see AssemblyConstants for the list of allowed registry names
     * @see AssemblyFunctions for the implementation of the check
     */
    private String handleRegistryName(String registryName) throws WrongRegistryNameException {
        return AssemblyFunctions.isRegistryName(registryName) ? registryName : null;
    }

    /**
     * Method to check the given registry value
     *
     * @param registryIndex Integer that represents a registry index to check
     * @return registry index if valid or -1 (not reachable due to exceptions)
     * @throws WrongRegistryIndexException if the given registry index is not valid
     * @see AssemblyConstants for the list of allowed registry indices
     * @see AssemblyFunctions for the implementation of the check
     */
    private int handleRegistryIndex(int registryIndex) throws WrongRegistryIndexException {
        return AssemblyFunctions.isRegistryIndex(registryIndex) ? registryIndex : -1;
    }

    /**
     * Method to check the given registry value
     *
     * @param registryIndex String that represents an expression to be evaluated as registry index
     * @return registry index if valid or -1 (not reachable due to exceptions)
     * @throws WrongRegistryIndexException if the given registry index is not valid
     */
    private int handleRegistryIndex(String registryIndex) throws WrongRegistryIndexException {
        if (registryIndex == null || registryIndex.equals(""))
            throw new WrongRegistryIndexException("\nNull-pointer registry index found");

        try {
            return handleRegistryIndex(AssemblyFunctions.eval(registryIndex));
        } catch (WrongExpressionException e) {
            throw new WrongRegistryIndexException("\nNon-valid registry index found: '" + registryIndex + "'"
                    + e.getMessage());
        }
    }
}
