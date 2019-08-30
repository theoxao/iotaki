package com.theoxao.core.config

import com.theoxao.base.model.BeanInfo
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

/**
 * @author theo
 * @date 19-8-30
 */
@Component
class BeanHelper(private val applicationContext: ApplicationContext) {

    private val bf = applicationContext.autowireCapableBeanFactory as DefaultListableBeanFactory

    fun registerBean(beanInfo: BeanInfo) {
        this.register(beanInfo.name, beanInfo.bean)
    }

    fun register(name: String, clazz: Class<*>) {
        bf.registerBeanDefinition(name, BeanDefinitionBuilder.genericBeanDefinition(clazz).beanDefinition)
    }

    fun register(clazz: Class<*>) {
        register(clazz.name, clazz)
    }

    inline fun <reified T> register(name: String) {
        register(name, T::class.java)
    }

    inline fun <reified T> register() {
        register<T>(T::class.java.name)
    }


}