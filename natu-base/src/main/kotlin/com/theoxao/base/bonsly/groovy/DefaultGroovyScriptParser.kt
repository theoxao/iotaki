package com.theoxao.base.bonsly.groovy

import groovy.lang.GroovyShell
import groovy.lang.Script
import org.springframework.stereotype.Component

/**
 * @author theoxao
 * @date 2019/5/28
 */
@Component
class DefaultGroovyScriptParser {

    private val shell = GroovyShell()

    private fun parse(content: String) = shell.parse(content)

    private fun autowired(script: Script): Script = TODO()

    fun process(content: String): Script {
        return autowired(parse(content))
    }

    fun methodName(): String = TODO()

}