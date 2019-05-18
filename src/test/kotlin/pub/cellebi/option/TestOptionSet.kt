package pub.cellebi.option

import org.junit.Before
import org.junit.Test
import pub.cellebi.option.basic.BoolValue

class TestOptionSet {
    val os = OptionSet("testOptionSet", "a test unit");

    @Before
    fun setup() {
        os.apply {
            postBoolOption("h", false, "this help");
            postBoolOption("v", false, "show version and exit");
            postStringOption("cp", "/Users/makhocheung", "set the `classpath`");
        }
    }

    @Test
    fun testShowUsage() {
        os.showUsage();
    }

    @Test
    fun testParseArgs() {
        os.parseArgs(arrayOf("-h", "-v"))
        val option = os.find("h");
        if (option != null) {
            val boolValue = option.getCustomType(BoolValue::class.java)

        }
    }

}