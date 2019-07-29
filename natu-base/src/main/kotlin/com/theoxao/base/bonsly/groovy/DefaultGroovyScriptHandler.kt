package com.theoxao.base.bonsly.groovy

import com.theoxao.base.bonsly.BaseGroovyScriptHandler
import com.theoxao.base.model.ScriptModel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component


@Component
class DefaultGroovyScriptHandler(
        applicationContext: ApplicationContext,
        private val defaultGroovyScriptParser: DefaultGroovyScriptParser
) : BaseGroovyScriptHandler(applicationContext) {

    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java.name)
    }

    init {
        supportedFileExtension = arrayOf("groovy", "java")
    }

    override fun handle(target: List<ScriptModel>) {
        target.forEach {
            val triggerName = it.config?.trigger?.get("name")
                    ?: "http"  // if trigger not supplied, using http as default
            val triggerHandler = triggers[triggerName]
            if (triggerHandler == null) {
                log.debug("trigger named \"$triggerName\" does not exist , ignore script ${it.scriptSource.url.path}")
                return@forEach
            }
            val script = defaultGroovyScriptParser.process(it.content)
            triggerHandler.handle(it) {
                val methodName = defaultGroovyScriptParser.methodName()
                val method = script.metaClass.theClass.methods.find { m -> m.name == methodName }
                        ?: throw RuntimeException("can not found method")
                script.invokeMethod(methodName, triggerHandler.parameters(method) {
                    ScriptParamNameDiscoverer(script).getParameterNames(method)
                })
            }
        }
    }

}