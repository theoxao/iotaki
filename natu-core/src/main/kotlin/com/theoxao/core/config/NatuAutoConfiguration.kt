package com.theoxao.core.config

import com.theoxao.base.aggron.BaseScriptLoader
import com.theoxao.base.bonsly.BaseScriptHandler
import com.theoxao.base.lileep.BaseTriggerHandler
import com.theoxao.base.model.Script
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration

@Configuration
open class NatuAutoConfiguration(
        applicationContext: ApplicationContext
) {

    private val scriptLoaders =
            applicationContext.getBeansOfType(BaseScriptLoader::class.java)

    private val scriptHandlers =
            applicationContext.getBeansOfType(BaseScriptHandler::class.java).values.associateBy { it.supportedFileExtension }

    private val triggerHandlers =
            applicationContext.getBeansOfType(BaseTriggerHandler::class.java).values.associateBy { it.name }

    private val scriptHandlerCache: MutableMap<String, BaseScriptHandler> = mutableMapOf()

    init {
        val scripts = loadScript()?.groupBy({ it.extension }, { it })
        scripts?.forEach { ext, ss ->
            var handler = scriptHandlerCache[ext]
            if (handler == null) {
                handler = findHandler(ext)
                handler?.let {
                    scriptHandlerCache[ext] = it
                }

            }
            handler?.triggers = triggerHandlers
            handler?.handle(ss)
        }
    }

    private fun findHandler(ext: String): BaseScriptHandler? {
        for (it in scriptHandlers) {
            if (it.key.contains(ext)) return it.value
        }
        return null
    }

    private fun loadScript(): List<Script>? {
        return scriptLoaders?.flatMap {
            it.value.load()
        }
    }

}