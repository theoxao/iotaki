package com.theoxao.aggron.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * @author theoxao
 * @date 2019/5/28
 */

@Configuration
@ConfigurationProperties(prefix = "aggron.file")
open class FileRootConfiguration {

    open var rootPath: String = "script"
    open var usePathAsPartOfUri = true

}