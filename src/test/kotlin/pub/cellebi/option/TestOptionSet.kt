package pub.cellebi.option

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue


class TestOptionSet {

    companion object {
        fun optionSetOf(): OptionSet {
            return OptionSet("test", "for unit test.")
        }
    }

    @Test
    fun testParse2Byte() {
        val set = optionSetOf().postByteOption("a", 1, "test")
        set.parse("-a 10".string2List())
        set.getByteOption("a").value expectEquals 10
    }

    @Test
    fun testParse2Short() {
        val set = optionSetOf().postShortOption("a", 1, "test")
        set.parse("-a 256".string2List())
        set.getShortOption("a").value expectEquals 256
    }

    @Test
    fun testParse2Int() {
        val set = optionSetOf().postIntOption("a", 0, "test")
        set.parse("-a 1".string2List())
        set.getIntOption("a").value expectEquals 1
    }

    @Test
    fun testParse2Long() {
        val set = optionSetOf().postLongOption("a", 1L, "test")
        set.parse("-a 250000000".string2List())
        set.getLongOption("a").value expectEquals 250000000L
    }

    @Test
    fun testParse2Float() {
        val set = optionSetOf().postFloatOption("a", 0.1F, "test")
        set.parse("-a 0.2".string2List())
        set.getFloatOption("a").value expectEquals 0.2F
    }

    @Test
    fun testParse2Double() {
        val set = optionSetOf().postDoubleOption("a", 0.1, "test")
        set.parse("-a 5.2".string2List())
        set.getDoubleOption("a").value expectEquals 5.2
    }

    @Test
    fun testParse2Bool() {
        val set = optionSetOf().postBoolOption("a", false, "test")
        set.parse("--a".string2List())
        set.getBoolOption("a").value.expectTrue()
        val set1 = optionSetOf().postBoolOption("a", false, "test")
        set1.parse("-a".string2List())
        set1.getBoolOption("a").value.expectTrue()
        val set2 = optionSetOf().postBoolOption("b", false, "test")
        set2.getBoolOption("b").value.expectFalse()
    }

    @Test
    fun testString() {
        val set = optionSetOf().postStringOption("a", "test", "test")
        set.parse("-a happy".string2List())
        set.getStringOption("a").value expectEquals "happy"
    }

    @Test
    fun testParse2Custom() {
        val set = optionSetOf()
                .postCustomOption {
                    StringListOption("a", arrayListOf("a", "b", "c"), "test")
                }
        set.parse("--a apple,banana,orange".string2List())
        set.getOptionByType("a", StringListOption::class.java).value expectEquals arrayListOf("apple", "banana", "orange")
    }

    @Test
    fun testCommand() {
        val args = "sayHi --lang English".string2List()
        val set = optionSetOf().apply {
            postBoolOption("a", false, "test")
        }
        set.parse(args)
        val commandSet = optionSetOf().apply {
            postStringOption("lang", "Chinese", "test")
        }
        if (args[0] == "sayHi") {
            commandSet.parse(args.slice(1 until args.size))
            when (commandSet.getStringOption("lang").value) {
                "Chinese" -> println("你好")
                "English" -> println("Hi")
                else -> throw AssertionError()
            }
        }
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


private fun String.string2List(): List<String> {
    return this.split(" ").toList();
}

fun Boolean.expectTrue(message: String? = null) = assertTrue(this, message)
fun Boolean.expectFalse(message: String? = null) = assertFalse(this, message)
infix fun <T> T.expectEquals(value: T) = assertEquals(value, this)