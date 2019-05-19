package pub.cellebi.option.type;

import pub.cellebi.option.Option;

public class IntOption extends Option<Integer> {

    public IntOption(String name, Integer value, String usage) {
        super(name, value, usage);
    }

    @Override
    protected String string(Integer value) {
        return String.valueOf(value);
    }

    @Override
    protected Integer parse(String strValue) {
        return Integer.valueOf(strValue);
    }
}
