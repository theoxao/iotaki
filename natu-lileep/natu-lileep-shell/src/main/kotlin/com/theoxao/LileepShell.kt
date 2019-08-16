package com.theoxao

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.launch
import org.springframework.core.ParameterNameDiscoverer
import org.springframework.shell.*
import java.lang.reflect.Method
import java.util.stream.Collectors

/**
 * @author theo
 * @date 19-8-15
 */

class LileepShell(private val resultHandler: ResultHandler<Any>) : Shell(resultHandler) {

    private val scriptMap =
            mutableMapOf<String, suspend (parameter: suspend (Method, ParameterNameDiscoverer) -> Array<*>?) -> Any?>()

    override fun evaluate(input: Input): Any {
        if (noInput(input)) {
            return NO_INPUT
        }
        val words = input.words()
        val line = input.words().stream().collect(Collectors.joining(" ")).trim { it <= ' ' }
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