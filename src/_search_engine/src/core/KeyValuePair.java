
package core;

public class KeyValuePair implements Comparable<KeyValuePair> 
{
    private String key;
    private double value;

    public KeyValuePair(String key, double value) 
    {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public double getValue() {
        return value;
    }

    @Override
    public int compareTo(KeyValuePair other) {
        // Compare based on the value in descending order
        return Double.compare(other.value, value);
    }
}
