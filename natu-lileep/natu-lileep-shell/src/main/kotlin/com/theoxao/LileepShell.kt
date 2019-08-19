package com.theoxao

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import org.springframework.core.ParameterNameDiscoverer
import org.springframework.shell.CommandNotFound
import org.springframework.shell.Input
import org.springframework.shell.Shell
import java.lang.reflect.Method
import java.util.stream.Collectors

/**
 * @author theo
 * @date 19-8-15
 */

class LileepShell(private val resultHandler: FutureResultHandler<Any>) : Shell(resultHandler) {

    private val scriptMap =
            mutableMapOf<String, suspend (parameter: suspend (Method, ParameterNameDiscoverer) -> Array<*>?) -> Any?>()

    private val embeddedCommand =
            mutableMapOf<String, () -> Any>()

    init {
        embeddedCommand["help"] = {
            "default: \n" +
                    embeddedCommand.keys.joinToString("\n") + "\n\n" +
                    "custom: \n" +
                    scriptMap.keys.joinToString("\n")
        }
    }

    suspend fun addScript(name: String, invoke: suspend (parameter: suspend (Method, ParameterNameDiscoverer) -> Array<*>?) -> Any?) {
        this.scriptMap[name] = invoke
    }

    override fun evaluate(input: Input): Any {
        if (noInput(input)) {
            return NO_INPUT
        }
        val words = input.words()
        val line = input.words().stream().collect(Collectors.joining(" ")).trim { it <= ' ' }
        if (embeddedCommand.containsKey(line)) {
            return embeddedCommand[line]?.invoke() ?: return CommandNotFound(words)
        }
        if (scriptMap.containsKey(line)) {
            return GlobalScope.future {
                scriptMap[line]?.invoke { _, _ ->
                    return@invoke arrayOf<Any>()
                }
            }
        }
        return CommandNotFound(words)
    }


    private fun noInput(input: Input): Boolean {
        return (input.words().isEmpty()
                || input.words().size == 1 && input.words()[0].trim { it <= ' ' }.isEmpty()
                || input.words().iterator().next().matches("\\s*//.*".toRegex()))
    }


}