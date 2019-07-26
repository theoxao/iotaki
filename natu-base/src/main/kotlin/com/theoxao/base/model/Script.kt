package com.theoxao.base.model

import com.theoxao.base.aggron.ScriptLoader

/**
 * @author theoxao
 * @date 2019/5/28
 */
class Script {
    var scriptSource: ScriptSource? = null

    lateinit var content: String

    lateinit var extension: String

    lateinit var loader: ScriptLoader
}