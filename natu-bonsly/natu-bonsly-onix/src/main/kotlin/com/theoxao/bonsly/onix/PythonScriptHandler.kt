package com.theoxao.bonsly.onix

import com.theoxao.base.bonsly.BaseGraalvmScriptHandler
import com.theoxao.base.model.BeanInfo
import com.theoxao.base.model.ScriptModel
import org.graalvm.polyglot.Context
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext

class PythonScriptHandler(
        private val applicationContext: ApplicationContext
) : BaseGraalvmScriptHandler() {


    companion object {
        val log = LoggerFactory.getLogger(this::class.java.name)
    }

    init {
        supportedFileExtension = arrayOf("py")
    }

    override suspend fun handle(target: List<ScriptModel>) {
        target.forEach {
            val context = Context.newBuilder("python").allowAllAccess(true).build()
            val triggerName = it.config?.trigger?.get("name") ?: "http"
            val triggerHandler = triggers[triggerName]
            if (triggerHandler == null) {
                log.debug("trigger named \"$triggerName\" does not exist , ignore script ${it.scriptSource.url.path}")
                return@forEach
            }
            triggerHandler.register(it) { _ ->
                context.eval("python", it.content)
            }
        }

    }

    override fun getBean(scriptModel: ScriptModel): BeanInfo {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}