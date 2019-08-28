package com.theoxao.base.lileep

import com.theoxao.base.model.ScriptModel
import org.springframework.core.ParameterNameDiscoverer
import java.lang.reflect.Method

/**
 * @author theoxao
 * @date 2019/5/28
 */
abstract class BaseTriggerHandler {

    lateinit var name: String

    abstract suspend fun register(scriptModel: ScriptModel, invokeScript: suspend (parameter: suspend (Method, ParameterNameDiscoverer) -> Array<*>?) -> Any?)

    abstract suspend fun unregister(hash: Int)
}