package com.theoxao.aggron

import com.theoxao.aggron.config.GithubConfiguration
import com.theoxao.base.aggron.BaseScriptLoader
import com.theoxao.base.model.ScriptModel
import com.theoxao.base.model.ScriptSource
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.util.KtorExperimentalAPI
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

/**
 * @author theo
 * @date 19-8-26
 */
@Component
class GithubFileLoader(private val gitConfig: GithubConfiguration) : BaseScriptLoader() {

    @KtorExperimentalAPI
    private val httpClient = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = JacksonSerializer()
        }
        engine {
            maxConnectionsCount = 1000
        }

    }

    override fun refreshAll() = TODO()

    override suspend fun load(): List<ScriptModel> {
        if (StringUtils.isEmpty(gitConfig.uri))
            return listOf()
        val url = buildUrl(gitConfig.path)
        val githubData = fetch(url)
        gitRouteCache = feedChild(githubData, gitConfig.path)
    }


    private suspend fun feedChild(parents: List<GithubData>, parentDir: String): List<ScriptModel> {
        val routeScripts = mutableListOf<ScriptModel>()
        parents.forEach {
            it.relativePath = it.path.removePrefix(gitConfig.path)
            if (it.type == "dir") {
                val path = parentDir + "/" + it.name
                it.child = fetch(buildUrl(path))
                routeScripts.addAll(feedChild(it.child, path))
            } else {
                val content = httpClient.get<String>(it.downloanUrl)
                val record = ScriptModel(
                        ScriptSource(it.downloadUrl, content),
                        content
                )

                routeScripts.add(record)
            }
        }
        return routeScripts
    }

    private suspend fun fetch(packageUrl: String) = httpClient.get<List<GithubData>>(packageUrl)

    private fun buildUrl(path: String): String {
        return "https://api.github.com/repos/${gitConfig.git.removePrefix("https://github.com/")}" +
                "/contents/$path?ref=${gitConfig.branch}"
    }
}