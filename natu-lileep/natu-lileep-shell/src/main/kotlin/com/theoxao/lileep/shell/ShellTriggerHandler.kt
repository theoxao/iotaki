package com.theoxao.lileep.shell

import com.theoxao.base.lileep.BaseTriggerHandler
import com.theoxao.base.model.ScriptModel
import org.springframework.core.ParameterNameDiscoverer
import org.springframework.shell.Shell
import org.springframework.stereotype.Component
import java.lang.reflect.Method

/**
 * @author theo
 * @date 19-8-13
 */
@Component
class ShellTriggerHandler(private val shell: Shell) : BaseTriggerHandler() {

    init {
        name = "shell"
    }

    override suspend fun handle(scriptModel: ScriptModel, invokeScript: suspend (parameter: suspend (Method, ParameterNameDiscoverer) -> Array<*>?) -> Any?) {
        val uri = scriptModel.scriptSource.url.path.removePrefix(scriptModel.loader.basePath ?: "")
        if (shell is LileepShell)
            shell.addScript(uri, invokeScript)
    }
}