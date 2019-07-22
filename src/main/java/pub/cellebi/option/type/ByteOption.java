package pub.cellebi.option.type;

import pub.cellebi.option.Option;

public class ByteOption extends Option<Byte> {

    public ByteOption(String name, Byte value, String usage) {
        super(name, value, usage);
    }

    @Override
    protected String string(Byte value) {
        return String.valueOf(value);
    }

    @Override
    protected Byte parse(String strValue) {
        return Byte.valueOf(strValue);
    }
}
