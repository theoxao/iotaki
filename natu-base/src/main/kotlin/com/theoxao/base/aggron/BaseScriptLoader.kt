package com.theoxao.base.aggron

import com.theoxao.base.model.ScriptModel

/**
 * @author theoxao
 * @date 2019/5/28
 */
abstract class BaseScriptLoader {

    var autoLoad = false

    var basePath: String? = null

    abstract fun refreshAll()

    abstract fun load(): List<ScriptModel>

    fun whenDelete(listener: (ScriptModel) -> Unit) {

    }

    fun whenCreate(listener: (ScriptModel) -> Unit) {

    }

    fun whenUpdate(listener: (ScriptModel) -> Unit) {

    }

}