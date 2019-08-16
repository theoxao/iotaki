package com.theoxao.bonsly.aj

import com.theoxao.base.bonsly.BaseScriptHandler
import com.theoxao.base.model.ScriptModel
import com.theoxao.bonsly.groovy.DefaultGroovyScriptHandler
import org.springframework.stereotype.Component

/**
 * @author theoxao
 * @date 2019/5/28
 */
@Component
class AsyncJavaScriptHandler(
        private val groovyScriptHandler: DefaultGroovyScriptHandler,
        private val preParser: PreParser
) : BaseScriptHandler() {

    init {
        supportedFileExtension = arrayOf("asyncj", "asyncjava", "asj")
    }

    override suspend fun handle(target: List<ScriptModel>) {
        target.forEach {
            it.content = preParser.preParse(it.content)
        }
        groovyScriptHandler.triggers = triggers
        groovyScriptHandler.handle(target)
    }

}