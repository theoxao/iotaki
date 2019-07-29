package com.theoxao.lileep.http

import com.theoxao.base.lileep.BaseTriggerHandler
import com.theoxao.base.model.ScriptModel
import groovy.lang.Script
import org.springframework.stereotype.Component

/**
 * @author theoxao
 * @date 2019/5/28
 */
@Component
class HttpTriggerHandler : BaseTriggerHandler() {
    override fun handle(scriptModel: ScriptModel, invokeScript: (any: Any) -> Any) = TODO()
}