package pub.cellebi.option.basic;

public class DoubleValue implements Value<Double> {

    private Double value;

    @Override
    public String string() {
        return String.valueOf(value);
    }

    @Override
    public void parse(String doubleValue) {
        value = Double.valueOf(doubleValue);
    }

    @Override
    public Double getValue() {
        return value;
    }

    public DoubleValue(Double doubleValue) {
        value = doubleValue;
    }


}
