package pub.cellebi.option;

import pub.cellebi.option.type.*;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * <p>
 * present a program and a set of  global options
 * or a command and a set of command options
 * </p>
 * <code>
 * OptionSet optionSet = new OptionSet();
 * </code>
 *
 * @author makhocheung
 * @see Option
 * @since 0.0.1
 */
public final class OptionSet {

    private final String name; //program name or command name
    private final Map<String, Option> postOptions = new HashMap<>();
    /**
     * 同等级的选项,通过顺序来决定优先级,例如version和help选项
     **/
    //private final Queue<Option> parsedOptions = new LinkedList<>();
    private final String usage;
    private PrintStream output = System.out; //system.out or system.err
    private String[] arguments;


    public OptionSet(String name, String usage) {
        this.name = name;
        this.usage = usage;
    }

    public <T extends Option> OptionSet postCustomOption(Supplier<T> supplier) {
        T supplyOption = supplier.get();
        postOption(supplyOption.getName(), supplyOption);
        return this;
    }

    public OptionSet postByteOption(String optionName, Byte value, String usage) {
        postOption(optionName, new ByteOption(name, value, usage));
        return this;
    }

    public OptionSet postShortOption(String optionName, Short value, String usage) {
        postOption(optionName, new ShortOption(optionName, value, usage));
        return this;
    }

    public OptionSet postIntOption(String optionName, Integer value, String usage) {
        postOption(optionName, new IntOption(optionName, value, usage));
        return this;
    }

    public OptionSet postLongOption(String optionName, Long value, String usage) {
        postOption(optionName, new LongOption(optionName, value, usage));
        return this;
    }

    public OptionSet postFloatOption(String optionName, Float value, String usage) {
        postOption(optionName, new FloatOption(optionName, value, usage));
        return this;
    }

    public OptionSet postDoubleOption(String optionName, Double value, String usage) {
        postOption(optionName, new DoubleOption(optionName, value, usage));
        return this;
    }

    public OptionSet postBoolOption(String optionName, Boolean value, String usage) {
        postOption(optionName, new BoolOption(optionName, value, usage));
        return this;
    }

    public OptionSet postStringOption(String optionName, String value, String usage) {
        postOption(optionName, new StringOption(optionName, value, usage));
        return this;
    }

    //may throw exception
    //TODO
    public OptionSet parseArgs(String[] argArray) {
        List<String> args = new ArrayList<>();
        Collections.addAll(args, argArray);
        parseArgs(args);
        return this;
    }

    private Option find(String optionName) {
        return postOptions.get(optionName);
    }

    /* TODO check and throw exception ??*/
    public ByteOption getByteOption(String optionName) {
        return (ByteOption) find(optionName);
    }

    public ShortOption getShortOption(String optionName) {
        return (ShortOption) find(optionName);
    }

    public IntOption getIntOption(String optionName) {
        return (IntOption) find(optionName);
    }

    public LongOption getLongOption(String optionName) {
        return (LongOption) find(optionName);
    }

    public FloatOption getFloatOption(String optionName) {
        return (FloatOption) find(optionName);
    }

    public DoubleOption getDoubleOption(String optionName) {
        return (DoubleOption) find(optionName);
    }

    public BoolOption getBoolOption(String optionName) {
        return (BoolOption) find(optionName);
    }

    public StringOption getStringOption(String optionName) {
        return (StringOption) find(optionName);
    }

    public <T extends Option> T getOptionByType(String optionName, Class<T> optionType) {
        return optionType.cast(find(optionName));
    }

    public boolean optionIsExist(String optionName) {
        return postOptions.containsKey(optionName);
    }

    public Map<String, Option> getActualOptions() {
        return postOptions;
    }

    public OptionSet setOutput(PrintStream output) {
        this.output = output;
        return this;
    }

    public String[] getRemainArguments() {
        return arguments;
    }

//    public Option peekOption() {
//        return parsedOptions.poll();
//    }

//    public boolean hasParsedOption() {
//        return !parsedOptions.isEmpty();
//    }

    public void showUsage() {
        output.printf("Usage of %s:\t\t%s\n", name, usage);
        showOptionsUsage();
    }

    private void showOptionsUsage() {
        postOptions.forEach((name, option) -> {
            String quoteVal = option.quoteUsageValue();
            name += quoteVal.isEmpty() ? "" : ' ' + quoteVal;
            output.printf(
                    "\t-%s" + (name.length() < 4 ? "\t\t" : "\n\t\t\t")
                            + "%s" + ((option instanceof BoolOption) ? "" : " (default \"%s\")") + "\n",
                    name, option.getUsage(), option.getDefaultValue()
            );
        });
    }

    public void showUsage(Consumer<OptionSet> handle) {
        //TODO
    }

    public String getName() {
        return name;
    }

    /* private method */

    private boolean parseArg(List<String> args, String arg) {
        if (arg.length() < 2 || !arg.startsWith("-")) {
            return false;
        }
        int n = 1;
        if (arg.startsWith("--")) {
            n++;
            if (arg.length() == 2) {
                return false;
            }
        }
        String optionName = arg.substring(n);
        String value;
        if (optionName.startsWith("-") || optionName.startsWith("=") || optionName.endsWith("=")) {
            return false;
        }
        int m;
        if (!postOptions.containsKey(optionName)) {
            throw new IllegalArgumentException(ErrorMsg.UNKNOWN_OPTION + ":" + optionName);
        }
        Option option = postOptions.get(optionName);
        if (option instanceof BoolOption) {
            //boolean
            value = BoolOption.reverseDefaultValue((BoolOption) option);
            m = 1;
        } else if (args.size() == 1 || args.get(1).startsWith("-")) {
            throw new IllegalArgumentException(ErrorMsg.UNKNOWN_OPTION + " : " + optionName);
        } else {
            value = args.get(1);
            m = 2;
        }
        option.setOptionValue(value);
        //parsedOptions.add(option);
        if (m == 2) {
            args.remove(0);
        }
        args.remove(0);
        return true;
    }

    private void postOption(String optionName, Option option) {
        postOptions.put(optionName, option);
    }

    private void parseArgs(List<String> args) {
        if (args == null) {
            throw new IllegalArgumentException(ErrorMsg.ARGS_INVALID);
        }
        if (args.size() == 0) {
            this.arguments = new String[0];
            return;
        }

        while (args.size() != 0) {
            if (!parseArg(args, args.get(0))) {
                break;
            }
        }
        this.arguments = args.toArray(new String[0]);
    }
}
