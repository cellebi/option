package pub.cellebi.option.basic;

public class IntValue implements Value<Integer> {

    private Integer value;

    @Override
    public String string() {
        return String.valueOf(value);
    }

    @Override
    public void parse(String intValue) {
        value = Integer.valueOf(intValue);
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public IntValue(Integer intValue) {
        value = intValue;
    }

}
