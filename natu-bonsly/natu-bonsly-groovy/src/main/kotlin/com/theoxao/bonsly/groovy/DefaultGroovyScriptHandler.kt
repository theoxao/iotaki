package com.theoxao.bonsly.groovy

import com.theoxao.base.bonsly.BaseGroovyScriptHandler
import com.theoxao.base.model.ScriptModel
import groovy.lang.MetaClass
import groovy.util.DelegatingScript
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
        val shell = BonslyGroovyShell()
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

            val methodName = defaultGroovyScriptParser.methodName()
            var obj: Any
            var metaClass: MetaClass
            if (parsed is DelegatingScript) {
                obj = parsed.delegate
                metaClass = InvokerHelper.getMetaClass(obj)
            } else {
                obj = parsed
                metaClass = parsed.metaClass
            }

            val method = metaClass.theClass.methods.firstOrNull { ce -> ce.name == methodName }
                    ?: throw RuntimeException("method $methodName not found exception")
            triggerHandler.handle(it) { parameter ->
                val result = metaClass.invokeMethod(obj, methodName, parameter.invoke(method, ScriptParamNameDiscoverer(metaClass, obj)))
                result
            }
        }
    }

}

