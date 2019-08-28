package com.theoxao.bonsly.onix

import com.theoxao.base.bonsly.BaseGraalvmScriptHandler
import com.theoxao.base.bonsly.groovy.DefaultGroovyScriptHandler
import com.theoxao.base.model.ScriptModel
import org.graalvm.polyglot.Context
import org.springframework.context.ApplicationContext

class PythonScriptHandler(
        private val applicationContext: ApplicationContext
) : BaseGraalvmScriptHandler() {



    init {
        supportedFileExtension = arrayOf("py")
    }

    override fun handle(target: List<ScriptModel>) {
        target.forEach {
            val context = Context.newBuilder("python").allowAllAccess(true).build()
            val triggerName = it.config?.trigger?.get("name") ?: "http"
            val triggerHandler = triggers[triggerName]
            if (triggerHandler == null) {
                DefaultGroovyScriptHandler.log.debug("trigger named \"$triggerName\" does not exist , ignore script ${it.scriptSource.url.path}")
                return@forEach
            }

            triggerHandler.register(it) { _ ->
                context.eval("python", it.content)
            }
        }

    }


}