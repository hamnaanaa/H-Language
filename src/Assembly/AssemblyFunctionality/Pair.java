package Assembly.AssemblyFunctionality;

/***
 * Class that represents a pair of two values
 * @param <K> first value
 * @param <V> second value
 */
public class Pair<K, V> {
    private K key;
    private V value;

    /**
     * Generic constructor for a pair, that takes two parameters:
     * @param key first value
     * @param value second value
     */
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + ":" + value;
    }
}
