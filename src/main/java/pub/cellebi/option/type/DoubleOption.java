package pub.cellebi.option.type;

import pub.cellebi.option.Option;

public class DoubleOption extends Option<Double> {

    public DoubleOption(String name, Double value, String usage) {
        super(name, value, usage);
    }

    @Override
    protected String string(Double value) {
        return String.valueOf(value);
    }

    @Override
    protected Double parse(String strValue) {
        return Double.valueOf(strValue);
    }
}
