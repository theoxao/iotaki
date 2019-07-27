package com.theoxao.bobsly.groovy

import com.theoxao.base.bonsly.BaseGroovyScriptHandler
import com.theoxao.base.model.Script
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component


@Component
class DefaultGroovyScriptHandler(
        applicationContext: ApplicationContext
) : BaseGroovyScriptHandler(applicationContext) {

    init {
        supportedFileExtension = arrayOf("groovy", "java")
    }


    override fun handle(target: List<Script>) {

    }

}