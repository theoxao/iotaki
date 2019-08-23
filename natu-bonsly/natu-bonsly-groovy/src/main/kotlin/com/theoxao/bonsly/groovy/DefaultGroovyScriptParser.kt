package com.theoxao.bonsly.groovy

import com.theoxao.bonsly.groovy.ast.AutowiredASTTransform.Companion.AUTOWIRE_BEAN
import groovy.lang.MetaClass
import org.codehaus.groovy.runtime.InvokerHelper
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

/**
 * @author theoxao
 * @date 2019/5/28
 */
@Component
class DefaultGroovyScriptParser(private val applicationContext: ApplicationContext) {

    private val shell = BonslyGroovyShell()

    fun parse(content: String) = shell.parse(content)

    fun autowired(meta: MetaClass, obj: Any) {
        val autowiredBeans = meta.invokeMethod(obj, AUTOWIRE_BEAN, InvokerHelper.EMPTY_ARGS) as List<String>
        autowiredBeans.forEach {
            val bean: Any? = applicationContext.getBean(it)
            bean?.let { _ ->
                meta.setProperty(obj, it, bean)
            }
        }
    }

    fun methodName(): String = "asyncJava"

}