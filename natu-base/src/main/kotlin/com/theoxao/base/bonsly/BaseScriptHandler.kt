package com.theoxao.base.bonsly

import com.theoxao.base.lileep.BaseTriggerHandler
import com.theoxao.base.model.BeanInfo
import com.theoxao.base.model.ScriptModel


abstract class BaseScriptHandler {

    lateinit var supportedFileExtension: Array<String>

    lateinit var triggers: Map<String, BaseTriggerHandler>

    abstract suspend fun handle(target: List<ScriptModel>)

    abstract fun getBean(scriptModel: ScriptModel): BeanInfo
}