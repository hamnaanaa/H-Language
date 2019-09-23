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

    public String getTokenName() {
        String className = this.getClass().toString();
        return className.substring(className.lastIndexOf(".") + 1);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Token && this.value.equals(((Token) obj).value);
    }
}
