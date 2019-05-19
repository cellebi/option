package pub.cellebi.option;

import java.util.Objects;

/**
 * @author makhocheung
 * @see OptionSet
 */
public abstract class Option<T> {

    private String name;  //option名
    private String usage;
    private String defaultVal;
    private T value;

    protected Option(String name, T value, String usage) {
        this.name = name;
        this.usage = usage;
        this.value = Objects.requireNonNull(value);
        this.defaultVal = string(value);
    }

    protected abstract String string(T value);

    protected abstract T parse(String strValue);

    final void setOptionValue(String strValue) {
        value = parse(strValue);
    }

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

    public String getUsage() {
        return usage;
    }

    public String getDefaultValue() {
        return defaultVal;
    }


}
