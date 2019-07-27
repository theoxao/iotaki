package com.theoxao.base.aggron

import com.theoxao.base.model.Script

/**
 * @author theoxao
 * @date 2019/5/28
 */
abstract class BaseScriptLoader {

    var autoLoad = false

    var basePath: String? = null

    abstract fun refreshAll()

    abstract fun load(): List<Script>

}