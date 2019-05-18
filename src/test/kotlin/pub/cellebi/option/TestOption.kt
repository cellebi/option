package pub.cellebi.option

import org.junit.*
import org.junit.Test
import pub.cellebi.option.basic.StringValue

class TestOption {
    @Test
    fun testQuoteUsageValue() {
        val option = Option("-cp", StringValue("/Users/makhocheung"), "set the `classpath`");
        Assert.assertEquals(option.quoteUsageValue(), "classpath");
    }
}