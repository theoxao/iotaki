package com.theoxao.base.bonsly

import com.theoxao.base.lileep.BaseTriggerHandler
import org.springframework.context.ApplicationContext

abstract class BaseGroovyScriptHandler(
        applicationContext: ApplicationContext
) : BaseScriptHandler() {

    val triggers = applicationContext.getBeansOfType(BaseTriggerHandler::class.java)

}