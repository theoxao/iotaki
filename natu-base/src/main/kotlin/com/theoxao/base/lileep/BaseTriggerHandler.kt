package com.theoxao.base.lileep

import com.theoxao.base.model.ScriptModel
import java.lang.reflect.Method

/**
 * @author theoxao
 * @date 2019/5/28
 */
abstract class BaseTriggerHandler {

    lateinit var name: String

    abstract fun handle(scriptModel: ScriptModel, invokeScript: (parameter: (Array<String>?) -> Array<Any?>?) -> Any)
}