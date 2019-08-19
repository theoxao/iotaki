package com.theoxao

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.shell.Shell
import org.springframework.stereotype.Component
import javax.annotation.Resource

/**
 * @author theo
 * @date 19-8-15
 */
@Component
class LileepShellPostProcessor : BeanPostProcessor {

    @Resource
    private lateinit var defaultListableBeanFactory: DefaultListableBeanFactory

    @Resource
    @Qualifier("future")
    private lateinit var futureResultHandler: FutureResultHandler<Any>

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {

        if (bean is Shell && beanName == "shell" && defaultListableBeanFactory.containsBean("shell")) {
            defaultListableBeanFactory.removeBeanDefinition("shell")
            defaultListableBeanFactory.registerBeanDefinition("shell", BeanDefinitionBuilder.genericBeanDefinition(LileepShell::class.java).beanDefinition)
            return LileepShell(futureResultHandler)
        }
        return bean
    }
}