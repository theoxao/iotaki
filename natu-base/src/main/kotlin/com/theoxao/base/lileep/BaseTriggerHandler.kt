package com.theoxao.base.lileep

import com.theoxao.base.model.ScriptModel
import groovy.lang.Script

/**
 * @author theoxao
 * @date 2019/5/28
 */
abstract class BaseTriggerHandler {
    abstract fun handle(scriptModel: ScriptModel, invokeScript: (any: Any) -> Any)

    lateinit var name: String
}