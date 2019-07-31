package com.theoxao.lileep.http

import com.theoxao.base.common.NatuConfig
import com.theoxao.base.lileep.BaseTriggerHandler
import com.theoxao.base.model.ScriptModel
import com.theoxao.configuration.handlerParam
import com.theoxao.lileep.http.URIType.ANNOTATION
import com.theoxao.lileep.http.URIType.PATH
import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.response.respond
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.ApplicationEngine
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.future.await
import org.springframework.core.ParameterNameDiscoverer
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import java.util.concurrent.CompletableFuture

/**
 * @author theoxao
 * @date 2019/5/28
 */
@Component
class HttpTriggerHandler(
        private val applicationEngine: ApplicationEngine
) : BaseTriggerHandler() {

    companion object {
        const val triggerTypeKey = "type"
    }

    init {
        name = "http"
    }

    @KtorExperimentalLocationsAPI
    override suspend fun handle(scriptModel: ScriptModel, invokeScript: suspend (parameter: suspend (Method, ParameterNameDiscoverer) -> Array<*>?) -> Any?) {
        val triggerConfig = scriptModel.config?.trigger
        val type = triggerConfig?.get(triggerTypeKey) ?: "path"
        if (type != "path") throw RuntimeException("currently, uri definition only support path type")
        val uri = scriptModel.scriptSource.url.path.removePrefix(scriptModel.loader.basePath ?: "")
        applicationEngine.application.routing {
            route(uri) {
                handle {
                    val result = invokeScript { method, discoverer ->
                        handlerParam(method, discoverer).map { it.value }.toTypedArray()
                    }
                    call.respond(when (result) {
                        is Unit -> ""
                        is CompletableFuture<*> -> result.nestedAwait()
                        is Deferred<*> -> result.await() ?: ""
                        null -> "null"
                        else -> result
                    })
                }
            }
        }

    }

    private suspend fun CompletableFuture<*>.nestedAwait(): Any {
        val await = this.await()
        if (await is CompletableFuture<*>) {
            return await.nestedAwait()
        }
        return await
    }


}