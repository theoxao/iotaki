package com.theoxao

import com.theoxao.base.lileep.BaseTriggerHandler
import com.theoxao.base.model.ScriptModel
import org.springframework.core.ParameterNameDiscoverer
import java.lang.reflect.Method

/**
 * @author theo
 * @date 19-8-13
 */

class ShellTriggerHandler : BaseTriggerHandler() {

    override suspend fun handle(scriptModel: ScriptModel, invokeScript: suspend (parameter: suspend (Method, ParameterNameDiscoverer) -> Array<*>?) -> Any?) {

    }
}