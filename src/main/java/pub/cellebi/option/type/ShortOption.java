package pub.cellebi.option.type;

import pub.cellebi.option.Option;

public class ShortOption extends Option<Short> {

    public ShortOption(String name, Short value, String usage) {
        super(name, value, usage);
    }

    @Override
    protected String string(Short value) {
        return String.valueOf(value);
    }

    @Override
    protected Short parse(String strValue) {
        return Short.valueOf(strValue);
    }
}
