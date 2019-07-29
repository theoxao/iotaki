package com.theoxao.base.bonsly.groovy

import groovy.lang.GroovyShell
import groovy.lang.Script

/**
 * @author theoxao
 * @date 2019/5/28
 */
class DefaultGroovyScriptParser {

    private val shell = GroovyShell()

    private fun parse(content: String) = shell.parse(content)

    private fun autowired(script: Script): Script = TODO()


}