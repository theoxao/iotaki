package com.theoxao.base.model

import com.theoxao.base.loader.ScriptLoader

/**
 * @author theoxao
 * @date 2019/5/28
 */
class Script {
    var scriptSource: ScriptSource? = null

    lateinit var content: String

    lateinit var loader: ScriptLoader
}