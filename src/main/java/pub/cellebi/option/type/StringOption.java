package pub.cellebi.option.type;

import pub.cellebi.option.Option;

public class StringOption extends Option<String> {

    public StringOption(String name, String value, String usage) {
        super(name, value, usage);
    }

    @Override
    protected String string(String value) {
        return value;
    }

    @Override
    protected String parse(String strValue) {
        return strValue;
    }
}
