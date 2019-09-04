package com.theoxao.base.bonsly

import com.theoxao.base.lileep.TriggerHandler
import com.theoxao.base.model.BeanInfo
import com.theoxao.base.model.ScriptModel


abstract class ScriptHandler {

    lateinit var supportedFileExtension: Array<String>

    lateinit var triggers: Map<String, TriggerHandler>

    abstract suspend fun handle(target: List<ScriptModel>)

    abstract fun getBean(scriptModel: ScriptModel): BeanInfo
}