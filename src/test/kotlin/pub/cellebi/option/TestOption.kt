package pub.cellebi.option

import org.junit.*
import org.junit.Test
import pub.cellebi.option.type.StringOption

class TestOption {

    @Test
    fun testQuoteUsageValue() {
        val option = StringOption("-cp", "/Users/makhocheung", "set the `classpath`");
        Assert.assertEquals(option.quoteUsageValue(), "classpath");
    }
}