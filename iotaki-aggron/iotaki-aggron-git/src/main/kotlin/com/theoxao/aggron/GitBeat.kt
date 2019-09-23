package com.theoxao.aggron

import com.theoxao.aggron.config.GithubConfiguration
import com.theoxao.aggron.model.GithubData
import io.ktor.client.request.get
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component


@KtorExperimentalAPI
@Component
class GitBeat(
        private val gitConfig: GithubConfiguration,
        private val githubFileLoader: GithubFileLoader
) {

    var currentSha = ""

    init {
        beat()
    }

    private fun beat() {
        GlobalScope.launch {
            while (true) {
                delay(gitConfig.beatRate * 1000L)
                val url = gitConfig.buildUrl(gitConfig.path)
                val data = httpClient.get<GithubData>(url)
                if (data.sha != currentSha) {
                    currentSha = data.sha
                    githubFileLoader.notifyChanged()
                }
            }
        }

    }

}
