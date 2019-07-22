package pub.cellebi.option

import org.junit.Test
import java.util.ArrayList
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
        optionSetOf()
                .postByteOption("a", 1, "test")
                .parseArgs("-a 10".toArrayOfSpace())
                .getByteOption("a")
                .value expectEquals 10
    }

    @Test
    fun testParse2Short() {
        optionSetOf()
                .postShortOption("a", 1, "test")
                .parseArgs("-a 256".toArrayOfSpace())
                .getShortOption("a")
                .value expectEquals 256
    }

    @Test
    fun testParse2Int() {
        optionSetOf()
                .postIntOption("a", 0, "test")
                .parseArgs("-a 1".toArrayOfSpace())
                .getIntOption("a")
                .value expectEquals 1
    }

    @Test
    fun testParse2Long() {
        optionSetOf()
                .postLongOption("a", 1L, "test")
                .parseArgs("-a 250000000".toArrayOfSpace())
                .getLongOption("a")
                .value expectEquals 250000000L
    }

    @Test
    fun testParse2Float() {
        optionSetOf()
                .postFloatOption("a", 0.1F, "test")
                .parseArgs("-a 0.2".toArrayOfSpace())
                .getFloatOption("a")
                .value expectEquals 0.2F
    }

    @Test
    fun testParse2Double() {
        optionSetOf()
                .postDoubleOption("a", 0.1, "test")
                .parseArgs("-a 5.2".toArrayOfSpace())
                .getDoubleOption("a")
                .value expectEquals 5.2
    }

    @Test
    fun testParse2Bool() {
        optionSetOf()
                .postBoolOption("a", false, "test")
                .parseArgs("--a".toArrayOfSpace())
                .getBoolOption("a").value.expectTrue()
        optionSetOf()
                .postBoolOption("a", false, "test")
                .parseArgs("-a".toArrayOfSpace())
                .getBoolOption("a").value.expectTrue()
        optionSetOf()
                .postBoolOption("b", false, "test")
                .getBoolOption("b").value.expectFalse()
    }

    @Test
    fun testString() {
        optionSetOf()
                .postStringOption("a", "test", "test")
                .parseArgs("-a happy".toArrayOfSpace())
                .getStringOption("a")
                .value expectEquals "happy"
    }

    @Test
    fun testParse2Custom() {
        optionSetOf()
                .postCustomOption {
                    StringListOption("a", arrayListOf("a", "b", "c"), "test")
                }.parseArgs("--a apple,banana,orange".toArrayOfSpace())
                .getOptionByType("a", StringListOption::class.java)
                .value expectEquals arrayListOf("apple", "banana", "orange")
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


private fun String.toArrayOfSpace(): Array<String> {
    return this.split(" ").toTypedArray()
}

fun Boolean.expectTrue(message: String? = null) = assertTrue(this, message)
fun Boolean.expectFalse(message: String? = null) = assertFalse(this, message)
infix fun <T> T.expectEquals(value: T) = assertEquals(value, this)