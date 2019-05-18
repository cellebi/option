import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;
import pub.cellebi.option.Option;
import pub.cellebi.option.OptionSet;
import pub.cellebi.option.Options;
import pub.cellebi.option.basic.BoolValue;
import pub.cellebi.option.basic.Value;

public class TheTest {
    @Test
    public void theTest() {
        OptionSet os = new OptionSet("testOptionSet", "a test unit");
        os.postBoolOption("h", false, "this help");
        os.postBoolOption("v", false, "show version and exit");
        os.postStringOption("cp", "/Users/makhocheung", "set the `classpath`");
        os.parseArgs(new String[]{"-h", "-v"});
        Option option = os.find("h");
        Value value = option.getOptionValue();
        Boolean bool = value.getTypeValue(Boolean.class);
    }
}
