package pub.cellebi.option;

import pub.cellebi.option.basic.*;

/**
 * @author makhocheung
 * @see OptionSet
 */
public class Option<T extends Value> {

    private String name;  //option名
    private T value;
    private String usage;
    private String defaultVal;

    public Option(String name, T value, String usage) {
        this.name = name;
        this.value = value;
        this.usage = usage;
        this.defaultVal = value.string();
    }

    public void setOptionValue(String strValue) {
        value.parse(strValue);
    }

    public T getOptionValue() {
        return value;
    }

    public String getUsage() {
        return usage;
    }

    public String getDefaultValue() {
        return defaultVal;
    }

    public String quoteUsageValue() {
        int start = usage.indexOf('`');
        int end = usage.lastIndexOf('`');
        if (start == -1 || start == end) {
            return "";
        } else {
            return usage.substring(start + 1, end);
        }
    }

    public BoolValue getBool() {
        return (BoolValue) value;
    }

    public DoubleValue getDouble() {
        return (DoubleValue) value;
    }

    public IntValue getInt() {
        return (IntValue) value;
    }

    public StringValue getString() {
        return (StringValue) value;
    }

    public static <E extends Value> E getCustomType(Class<E> type, Value value) {
        if (type.isInstance(value)) {
            return type.cast(value);
        }
        return null;
    }


}
