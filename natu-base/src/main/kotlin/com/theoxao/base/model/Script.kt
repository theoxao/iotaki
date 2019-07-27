package com.theoxao.base.model

import com.theoxao.base.aggron.ScriptLoader

/**
 * @author theoxao
 * @date 2019/5/28
 */
class Script(
        var scriptSource: ScriptSource,
        var content: String,
        var extension: String,
        var loader: ScriptLoader
)