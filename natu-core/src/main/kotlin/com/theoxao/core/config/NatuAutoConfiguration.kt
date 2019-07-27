package com.theoxao.core.config

import com.theoxao.base.aggron.BaseScriptLoader
import com.theoxao.base.bonsly.BaseScriptHandler
import com.theoxao.base.model.Script
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration
import java.util.function.Function

@Configuration
open class NatuAutoConfiguration(
        applicationContext: ApplicationContext
) {

    private val scriptLoaders = applicationContext.getBeansOfType(BaseScriptLoader::class.java)

    private val scriptHandlers = applicationContext.getBeansOfType(BaseScriptHandler::class.java)

    init {
        val scripts = loadScript()?.groupBy({ it.extension }, { it })
        TODO()
    }

    private fun loadScript(): List<Script>? {
        return scriptLoaders?.flatMap {
            it.value.load()
        }
    }

}