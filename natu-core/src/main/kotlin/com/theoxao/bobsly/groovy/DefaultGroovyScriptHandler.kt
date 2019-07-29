package com.theoxao.bobsly.groovy

import com.theoxao.base.bonsly.BaseGroovyScriptHandler
import com.theoxao.base.model.Script
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component


@Component
class DefaultGroovyScriptHandler(
        applicationContext: ApplicationContext
) : BaseGroovyScriptHandler(applicationContext) {

    companion object {
        val log = LoggerFactory.getLogger(this::class.java.name)
    }

    init {
        supportedFileExtension = arrayOf("groovy", "java")
    }


    override fun handle(target: List<Script>) {
        target.forEach {
            val triggerName = it.config?.trigger?.get("name")
                    ?: "http"  // if trigger not supplied, using http as default
            val triggerHandler = triggers[triggerName]
            if (triggerHandler == null) {
                log.debug("trigger named \"$triggerName\" does not exist , ignore script ${it.scriptSource.url.path}")
                return@forEach
            }

        }
    }

}