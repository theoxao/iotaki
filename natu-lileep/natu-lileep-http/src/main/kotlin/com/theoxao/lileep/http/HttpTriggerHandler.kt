package com.theoxao.lileep.http

import com.theoxao.base.lileep.BaseTriggerHandler
import com.theoxao.base.model.ScriptModel
import org.springframework.stereotype.Component
import java.lang.reflect.Method

/**
 * @author theoxao
 * @date 2019/5/28
 */
@Component
class HttpTriggerHandler : BaseTriggerHandler() {
    init {
        name = "http"
    }

    override fun handle(scriptModel: ScriptModel, invokeScript: (parameter: (Array<String>?) -> Array<Any?>?) -> Any) = TODO()

}