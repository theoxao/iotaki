package com.theoxao.lileep.amqp

import com.isolate.ListenerConfig
import com.theoxao.base.lileep.TriggerHandler
import com.theoxao.base.model.ScriptModel
import org.springframework.context.ApplicationContext
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.core.ParameterNameDiscoverer
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import java.util.*
import javax.annotation.Resource

/**
 * @author theo
 * @date 19-8-20
 */
val cache =
        mutableMapOf<String, suspend (parameter: suspend (Method, ParameterNameDiscoverer) -> Array<*>?) -> Any?>()

val children = mutableMapOf<String, ConfigurableApplicationContext>()

val hashMap = mutableMapOf<Int, String>()

@Component
class AmqpTriggerHandler : TriggerHandler() {

    override suspend fun unregister(hash: Int) {
        val key = hashMap[hash]
        cache.remove(key)
        key?.let { stop(it) }
    }

    @Resource
    private lateinit var context: ApplicationContext

    init {
        name = "amqp"
    }

    override suspend fun register(scriptModel: ScriptModel, invokeScript: suspend (parameter: suspend (Method, ParameterNameDiscoverer) -> Array<*>?) -> Any?) {
        val key = scriptModel.scriptSource.url.path.removePrefix(scriptModel.loader.basePath ?: "")
        println(key)
        cache[key] = invokeScript
        children[key] = addListener(key)
    }

    private fun stop(key: String) = children[key]?.stop()

    private fun addListener(queue: String): AnnotationConfigApplicationContext {
        val child = AnnotationConfigApplicationContext()
        child.parent = context
        val environment = child.environment
        val properties = Properties()
        properties.setProperty("queue.name", queue)
        val pps = PropertiesPropertySource("props", properties)
        environment.propertySources.addLast(pps)
        child.register(ListenerConfig::class.java)
        child.refresh()
        return child
    }

}