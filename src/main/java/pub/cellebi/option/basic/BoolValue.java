package pub.cellebi.option.basic;


public class BoolValue implements Value<Boolean> {

    private Boolean value;

    @Override
    public String string() {
        return String.valueOf(value);
    }

    @Override
    public void parse(String boolValue) {
        value = Boolean.valueOf(boolValue);
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    public BoolValue(Boolean boolValue) {
        value = boolValue;
    }
}
