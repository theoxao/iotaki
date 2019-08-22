package com.theoxao.bonsly.groovy

import com.theoxao.base.bonsly.BaseGroovyScriptHandler
import com.theoxao.base.model.ScriptModel
import com.theoxao.bonsly.groovy.ast.AutowiredASTTransform.Companion.AUTOWIRE_BEAN_SUFFIX
import groovy.lang.GroovyClassLoader
import groovy.lang.GroovyShell
import groovy.lang.GroovySystem
import org.codehaus.groovy.runtime.InvokerHelper
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
        val shell = GroovyShell(this::class.java.classLoader)
    }

    init {
        supportedFileExtension = arrayOf("groovy", "java")
    }

    override suspend fun handle(target: List<ScriptModel>) {
        target.forEach {
            val triggerName = it.config?.trigger?.get("name") ?: "http"
            val triggerHandler = triggers?.get(triggerName)
            if (triggerHandler == null) {
                log.debug("trigger named \"$triggerName\" does not exist , ignore script ${it.scriptSource.url.path}")
                return@forEach
            }
            val parsed = shell.parse(it.content)
            val parseClass = GroovyClassLoader().parseClass(it.content, it.scriptSource.url.path)
            val metaClass = InvokerHelper.getMetaClass(parseClass)
            val methodName = defaultGroovyScriptParser.methodName()
            val method = metaClass.theClass.methods.find { m -> m.name == methodName }
                    ?: throw RuntimeException("exterminate")
            metaClass.theClass.declaredFields
                    .filter { ce -> ce.name.endsWith(AUTOWIRE_BEAN_SUFFIX) }
            triggerHandler.handle(it) { parameter ->
                InvokerHelper.invokeMethod(parseClass, methodName, parameter(method, ScriptParamNameDiscoverer(parseClass)))
            }
        }
    }

}

