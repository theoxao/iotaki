package com.theoxao.aggron

import com.theoxao.aggron.config.GithubConfiguration
import com.theoxao.aggron.model.GithubData
import com.theoxao.base.aggron.ScriptLoader
import com.theoxao.base.common.IotakiConfig
import com.theoxao.base.model.ScriptModel
import com.theoxao.base.model.ScriptSource
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.util.KtorExperimentalAPI
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.yaml.snakeyaml.Yaml
import java.net.URI

/**
 * @author theo
 * @date 19-8-26
 */


val httpClient = HttpClient(CIO) {
    install(JsonFeature) {
        serializer = JacksonSerializer()
    }
    engine {
        maxConnectionsCount = 1000
    }

}

@KtorExperimentalAPI
@Component
class GithubFileLoader(private val gitConfig: GithubConfiguration) : ScriptLoader() {

    companion object {
        val log = LoggerFactory.getLogger(this::class.java.name)!!
    }


    init {
        basePath = gitConfig.uri.removePrefix("https://github.com/") + gitConfig.branch
    }

    override fun refreshAll() = TODO()

    override suspend fun load(): List<ScriptModel> {
        if (StringUtils.isEmpty(gitConfig.uri)) return listOf()
        val githubData = fetch(gitConfig.buildUrl(gitConfig.path))
        return feedChild(githubData, gitConfig.path, null)
    }


    override fun notifyChange() = TODO()

    private suspend fun feedChild(parents: List<GithubData>, parentDir: String, config: IotakiConfig?): List<ScriptModel> {
        val routeScripts = mutableListOf<ScriptModel>()
        val iotakiData = parents.firstOrNull { it.name == iotakiFileName && it.type != "dir" }
        val iotaki = iotakiData?.downloadUrl?.let { url ->
            log.info("fetching @ $url now")
            httpClient.get<String>(url)
        }
        val currentConfig = iotaki?.let { Yaml().loadAs(iotaki, IotakiConfig::class.java) } ?: config
        parents.forEach {
            currentConfig?.let { conf ->
                if (conf.ignore.contains(it.name)) {
                    return@forEach
                }
            }
            if (it.name == iotakiFileName && it.type != "dir") {
                return@forEach
            }
            it.relativePath = it.path.removePrefix(gitConfig.path)
            if (it.type == "dir") {
                val path = parentDir + "/" + it.name
                it.child = fetch(gitConfig.buildUrl(path))
                routeScripts.addAll(feedChild(it.child, path, currentConfig))
            } else {
                val content = it.downloadUrl?.let { url ->
                    log.info("fetching @ $url now")
                    httpClient.get<String>(url)
                } ?: ""
                val record = ScriptModel(
                        ScriptSource(URI(it.downloadUrl), content, it.sha),
                        content,
                        extension(it.downloadUrl),
                        this,
                        currentConfig
                )
                routeScripts.add(record)
            }
        }
        return routeScripts
    }

    private suspend fun fetch(packageUrl: String) = httpClient.get<List<GithubData>>(packageUrl)


    suspend fun notifyChanged() {

    }

    private fun extension(path: String?): String {
        if (path == null) return ""
        val dot = path.lastIndexOf(".")
        if (dot < 0) return ""
        return path.substring(dot + 1)
    }
}


fun GithubConfiguration.buildUrl(path: String): String {
    return "https://api.github.com/repos/${this.uri.removePrefix("https://github.com/")}" +
            "/contents/$path?ref=${this.branch}"
}
