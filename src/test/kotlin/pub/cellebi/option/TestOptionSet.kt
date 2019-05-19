package pub.cellebi.option

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class TestOptionSet {
    @Before
    fun setup() {
    }

    @Test
    fun testParseArgs() {
        arrayListOf(
                "testApp --version",
                "testApp --help",
                "testApp sayHi --lang 0",
                "testApp sayHi --lang 1",
                "testApp sayHi --lang 2",
                "testApp sayHi --lang 1 --case b"
        ).forEach {
            Assert.assertTrue(action(it.split(" ")))
        }
    }

    private fun action(command: List<String>): Boolean {
        val app = OptionSet("testApp", "a test unit")
        val command1 = OptionSet("sayHi", "test to say hi in different language")
        app.apply {
            postBoolOption("help", false, "show help message")
            postBoolOption("version", false, "show the test version")
        }
        command1.apply {
            postIntOption("lang", 0, """
                set the language
                            0 : chinese
                            1 : english
                            2 : japanese
            """.trimIndent())
            postStringOption("case", "a", "the case")
        }
        if (command.isEmpty()) {
            return false;
        }
        val args = command.subList(1, command.size)
        app.parseArgs(args.toTypedArray())
        if (app.hasParsedOption()/* 判断是否有提供的选项 */) {
            when (app.peekOption().name) {
                "version" -> println("version : 0.0.1")
                "help" -> app.showUsage()
            }
            return true
        }
        val remainArgs = app.remainArguments
        if (remainArgs.isNotEmpty() && remainArgs[0] == command1.name) {
            command1.parseArgs(remainArgs.slice(1 until remainArgs.size).toTypedArray())
            val lang = command1.getIntOption("lang")
            val case = command1.getStringOption("case").value
            println(when (lang.value) {
                0 -> "你好"
                1 -> if (case == "b") {
                    "hi".toUpperCase()
                } else {
                    "hi"
                }
                2 -> "こんにちは"
                else -> ""
            })
            return true
        }
        return false
    }
}