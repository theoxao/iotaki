package com.theoxao.aggron.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component


/**
 * @author theo
 * @date 2019/6/28
 */
@Configuration
@Component
@ConfigurationProperties(prefix = "natu.aggron.github")
open class GithubConfiguration {
    var uri = ""
    var branch = "master"
    var path = ""
    var beatRate = 1
    var packageAsRoute = false
}
