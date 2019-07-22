package pub.cellebi.option.type;

import pub.cellebi.option.Option;

public class FloatOption extends Option<Float> {

    public FloatOption(String name, Float value, String usage) {
        super(name, value, usage);
    }

    @Override
    protected String string(Float value) {
        return String.valueOf(value);
    }

    @Override
    protected Float parse(String strValue) {
        return Float.valueOf(strValue);
    }
}
