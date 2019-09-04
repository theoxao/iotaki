package com.theoxao.bonsly.groovy

import com.theoxao.bonsly.groovy.ast.AutowiredASTTransform.Companion.AUTOWIRE_BEAN
import com.theoxao.bonsly.groovy.ast.TransactionASTTransform.Companion.TRANSACTION_BEAN_NAME
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
        if (meta.methods.firstOrNull { it.name == "\$autowireBeans" } == null) return
        val autowiredBeans = autowiredBeans(meta, obj)
        autowiredBeans.forEach {
            val bean: Any? = if (it != TRANSACTION_BEAN_NAME) applicationContext.getBean(it)
            else try {
                applicationContext.getBean(Class.forName("org.springframework.transaction.PlatformTransactionManager"))
            } catch (e: ClassNotFoundException) {
                null
            }
            bean?.let { _ ->
                meta.setProperty(obj, it, bean)
            }
        }
    }

    fun autowiredBeans(meta: MetaClass, obj: Any): List<String> {
        if (meta.methods.firstOrNull { it.name == "\$autowireBeans" } == null) return arrayListOf()
        return meta.invokeMethod(obj, AUTOWIRE_BEAN, InvokerHelper.EMPTY_ARGS) as List<String>
    }

    fun methodName(): String = "asyncJava"

}