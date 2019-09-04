package com.theoxao.base.aggron

import com.theoxao.base.model.ScriptModel

/**
 * @author theoxao
 * @date 2019/5/28
 */
abstract class ScriptLoader {
    companion object {
        const val iotakiFileName = "iotaki.yaml"
    }

    var autoLoad = false

    var basePath: String? = null

    abstract fun refreshAll()

    abstract fun notifyChange()

    abstract suspend fun load(): List<ScriptModel>

    fun whenDelete(listener: (ScriptModel) -> Unit) {

    }

    fun whenCreate(listener: (ScriptModel) -> Unit) {

    }

    fun whenUpdate(listener: (ScriptModel) -> Unit) {

    }

}
