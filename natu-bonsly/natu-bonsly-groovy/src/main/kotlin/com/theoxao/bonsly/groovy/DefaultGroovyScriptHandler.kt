package com.theoxao.bonsly.groovy

import com.theoxao.base.bonsly.BaseGroovyScriptHandler
import com.theoxao.base.model.ScriptModel
import groovy.lang.GroovyClassLoader
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

    override suspend fun handle(target: List<ScriptModel>) {
        target.forEach {
            val triggerName = it.config?.trigger?.get("name") ?: "http"
            val triggerHandler = triggers[triggerName]
            if (triggerHandler == null) {
                log.debug("trigger named \"$triggerName\" does not exist , ignore script ${it.scriptSource.url.path}")
                return@forEach
            }
            val parseClass = GroovyClassLoader().parseClass(it.content)
            val script = defaultGroovyScriptParser.parse(it.content)
//            val script = defaultGroovyScriptParser.process(it.content)
            triggerHandler.handle(it) { parameter ->
                val methodName = defaultGroovyScriptParser.methodName()
                val method = parseClass.methods.find { m -> m.name == methodName }
                        ?: throw RuntimeException("exterminate")
                val instance = parseClass.newInstance()
                method.invoke(instance, parameter(method, ScriptParamNameDiscoverer(script)))
//                script.invokeMethod(methodName, parameter(method, ScriptParamNameDiscoverer(script)))
            }
        }
    }

}

