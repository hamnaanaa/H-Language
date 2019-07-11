package Assembly.AssemblyTokens;

/***
 * Abstract class that represents tokens in H-Language assembly instructions
 * @param <T> value type
 */
public abstract class Token<T> {
    /**
     * value of the given token
     */
    protected T value;


    @Override
    public String toString() {
        return value.toString();
    }
}
