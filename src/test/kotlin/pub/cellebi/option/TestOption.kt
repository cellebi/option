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

    @Test
    fun testCustomOption() {
        val command = OptionSet("show", "show the list")
        command.apply {
            postCustomOption {
                StringListOption("list", mutableListOf(""), "the list")
            }
        }
        command.parseArgs(arrayOf("--list", "apple,banana,grape,pineapple"))
        println(command.getOptionByType("list",StringListOption::class.java).value)
        println(command.peekOption().value)
    }


}

class StringListOption(name: String, value: List<String>?, usage: String) : Option<List<String>>(name, value, usage) {

    override fun string(value: List<String>?): String {
        return value!!.joinToString(",")
    }

    override fun parse(strValue: String?): List<String> {
        return strValue!!.split(",").toList()
    }

}