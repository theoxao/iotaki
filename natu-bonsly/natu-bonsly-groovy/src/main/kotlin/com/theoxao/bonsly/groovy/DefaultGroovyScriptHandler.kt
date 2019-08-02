package com.theoxao.bonsly.groovy

import com.theoxao.base.bonsly.BaseGroovyScriptHandler
import com.theoxao.base.model.ScriptModel
import groovy.lang.GroovyClassLoader
import org.checkerframework.common.reflection.qual.Invoke
import org.codehaus.groovy.runtime.InvokerHelper
import org.omg.CORBA.portable.InvokeHandler
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import java.lang.Exception


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
//            val ih= script as InvokerHelper
            val metaClass = InvokerHelper.getMetaClass(parseClass)
            val methodName = defaultGroovyScriptParser.methodName()
            val method = metaClass.theClass.methods.find { m -> m.name == methodName }
                    ?: throw RuntimeException("exterminate")
            triggerHandler.handle(it) { parameter ->
                val instance = parseClass.newInstance()
                InvokerHelper.invokeMethod(parseClass, methodName, parameter(method, ScriptParamNameDiscoverer(script)))
            }
        }
    }

}

