package pub.cellebi.option.type;

import pub.cellebi.option.Option;

public class LongOption extends Option<Long> {

    public LongOption(String name, Long value, String usage) {
        super(name, value, usage);
    }

    @Override
    protected String string(Long value) {
        return String.valueOf(value);
    }

    @Override
    protected Long parse(String strValue) {
        return Long.valueOf(strValue);
    }
}
