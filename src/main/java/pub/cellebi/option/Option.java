package pub.cellebi.option;

import java.util.Objects;

/**
 * @author makhocheung
 * @see OptionSet
 */
public abstract class Option<T> {

    private final String name;
    private final String usage;
    private final String defaultValue;
    private T value;

    protected Option(String name, T value, String usage) {
        this.name = name;
        this.usage = usage;
        this.value = Objects.requireNonNull(value);
        this.defaultValue = string(value);
    }

    /**
     * return the string value ot the option value
     */
    protected abstract String string(T value);

    /**
     * parse string value which user inputs to the T type
     */
    protected abstract T parse(String strValue);

    final void setOptionValue(String strValue) {
        value = parse(strValue);
    }

    /**
     * get quote usage value
     */
    public final String quoteUsageValue() {
        int start = usage.indexOf('`');
        int end = usage.lastIndexOf('`');
        if (start == -1 || start == end) {
            return "";
        } else {
            return usage.substring(start + 1, end);
        }
    }

    public final String getName() {
        return name;
    }

    public final T getValue() {
        return value;
    }

    final String getUsage() {
        return usage;
    }

    public final String getDefaultValue() {
        return defaultValue;
    }


}
