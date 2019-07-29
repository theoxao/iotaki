package com.theoxao.base.lileep

import com.theoxao.base.model.ScriptModel
import java.lang.reflect.Method

/**
 * @author theoxao
 * @date 2019/5/28
 */
abstract class BaseTriggerHandler {

    abstract fun handle(scriptModel: ScriptModel, invokeScript: (any: Any) -> Any)

    abstract fun parameters(method: Method?, discovery: () -> Array<String>?): Array<Any?>?

    lateinit var name: String
}