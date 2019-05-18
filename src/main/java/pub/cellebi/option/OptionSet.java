package pub.cellebi.option;

import org.jetbrains.annotations.Nullable;
import pub.cellebi.option.basic.*;

import java.io.BufferedWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;

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
public class OptionSet {

    private String name; //program name or command name
    private Map<String, Option> postOptions = new HashMap<>();
    private Map<String, Option> actualOptions = new HashMap<>();
    private String[] arguments;
    private PrintStream output = System.out; //system.out or system.err
    private String usage;

    public OptionSet(String name, String usage) {
        this.name = name;
        this.usage = usage;
    }

    public <T extends Value> void postOption(String optionName, T val, String usage) {
        postOptions.put(optionName, new Option<>(optionName, val, usage));
    }

    @SuppressWarnings("unchecked")
    public <T extends Value> T postCustomOption(String optionName, String value, String usage, Class<T> valueClass) {
        try {
            Constructor constructor = valueClass.getConstructor(String.class);
            T instance = (T) constructor.newInstance(value);
            postOption(optionName, instance, usage);
            return instance;
        } catch (Exception e) {
            //TODO
            e.printStackTrace();
            return null;
        }
    }

    public <T extends Value> void postCustomOption(String optionName, String usage, Supplier<T> supplier) {
        T suppliedValue = supplier.get();
        postOption(optionName, suppliedValue, usage);
    }

    public OptionSet postBoolOption(String optionName, Boolean value, String usage) {
        postOption(optionName, new BoolValue(value), usage);
        return this;
    }

    public OptionSet postIntOption(String optionName, Integer value, String usage) {
        postOption(optionName, new IntValue(value), usage);
        return this;
    }

    public OptionSet postDoubleOption(String optionName, Double value, String usage) {
        postOption(optionName, new DoubleValue(value), usage);
        return this;
    }

    public OptionSet postStringOption(String optionName, String value, String usage) {
        postOption(optionName, new StringValue(value), usage);
        return this;
    }

    //may throw exception
    //TODO
    public void parseArgs(String[] argArray) {
        List<String> args = new ArrayList<>();
        Collections.addAll(args, argArray);
        parseArgs(args);
    }

    @Nullable
    public Option find(String optionName) {
        return actualOptions.get(optionName);
    }

    public boolean optionIsExist(String optionName) {
        return actualOptions.containsKey(optionName);
    }

    public Map<String, Option> getActualOptions() {
        return actualOptions;
    }

    public OptionSet setOutput(PrintStream output) {
        this.output = output;
        return this;
    }

    public String[] getRemainArguments() {
        return arguments;
    }

    public void showUsage() {
        output.printf("Usage of %s:\t\t%s\n", name, usage);
        showOptionsUsage();
    }

    public void showOptionsUsage() {
        postOptions.forEach((name, option) -> {
            String quoteVal = option.quoteUsageValue();
            name += quoteVal.isEmpty() ? "" : ' ' + quoteVal;
            output.printf(
                    "\t-%s" + (name.length() < 4 ? "\t\t" : "\n\t\t\t")
                            + "%s" + ((option.getOptionValue() instanceof BoolValue) ? "" : " (default \"%s\")") + "\n",
                    name, option.getUsage(), option.getDefaultValue()
            );
        });
    }

    public void showUsage(Consumer<OptionSet> handle) {
        //TODO
    }

    /* private methods */
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

    private boolean parseArg(List<String> args, String arg) {
        int n = 0;
        if (arg.length() < 2 || !arg.startsWith("-")) {
            return false;
        }
        n++;
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
        if (optionName.contains("=")) {
            //boolean
            String[] keyValue = optionName.split("=");
            optionName = keyValue[0];
            value = keyValue[1]; //only "true" or "false", otherwise exception occurs
            m = 1;
        } else if (args.size() == 1 || args.get(1).startsWith("-")) {
            //boolean
            value = "true";
            m = 1;
        } else {
            value = args.get(1);
            m = 2;
        }
        if (!postOptions.containsKey(optionName)) {
            throw new IllegalArgumentException(ErrorMsg.OPTION_NO_EXIST);
        }
        Option option = postOptions.get(optionName);
        if (option.getOptionValue() instanceof BoolValue && m == 2) {
            throw new IllegalArgumentException(ErrorMsg.BOOL_OPTION_INVALID);
        }
        option.setOptionValue(value);
        actualOptions.put(optionName, option);
        if (m == 2) {
            args.remove(0);
        }
        args.remove(0);
        return true;
    }
}
