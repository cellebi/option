package pub.cellebi.option.basic;

import java.util.Objects;

public class StringValue implements Value<String> {

    private String value;

    @Override
    public String string() {
        return value;
    }

    @Override
    public void parse(String stringValue) {
        value = Objects.requireNonNull(stringValue);
    }

    @Override
    public String getValue() {
        return value;
    }

    public StringValue(String stringValue) {
        value = stringValue;
    }
}
