package pub.cellebi.option.type;

import pub.cellebi.option.Option;

public class BoolOption extends Option<Boolean> {

    private static final String TRUE_STR = "true";
    private static final String FALSE_STR = "false";

    public static String reverseDefaultValue(BoolOption boolOption) {
        return TRUE_STR.equals(boolOption.getDefaultValue()) ? FALSE_STR : TRUE_STR;
    }

    public BoolOption(String name, Boolean value, String usage) {
        super(name, value, usage);
    }

    @Override
    protected String string(Boolean value) {
        return String.valueOf(value);
    }

    @Override
    protected Boolean parse(String strValue) {
        return Boolean.valueOf(strValue);
    }


}
