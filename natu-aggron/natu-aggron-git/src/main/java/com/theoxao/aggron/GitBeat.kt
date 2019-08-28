package com.theoxao.aggron

import com.theoxao.aggron.config.GithubConfiguration
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

    init {
        beat()
    }

    fun beat() {
        var currentSha = ""
        GlobalScope.launch {
            while (true) {
                delay(gitConfig.beatRate * 1000L)

            }
        }

    }


}