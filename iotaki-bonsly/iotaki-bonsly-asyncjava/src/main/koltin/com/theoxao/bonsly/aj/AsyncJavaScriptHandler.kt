package com.theoxao.bonsly.aj

import com.theoxao.base.bonsly.ScriptHandler
import com.theoxao.base.model.BeanInfo
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
) : ScriptHandler() {

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

    override fun getBean(scriptModel: ScriptModel): BeanInfo {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}