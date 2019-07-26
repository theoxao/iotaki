package com.theoxao.base.loader

import com.theoxao.base.model.Script

/**
 * @author theoxao
 * @date 2019/5/28
 */
abstract class ScriptLoader {

    var autoLoad = false

    var basePath: String? = null

    abstract fun refreshAll()

    abstract fun load(): List<Script>

}