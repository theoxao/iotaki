package com.theoxao.base.model

import com.theoxao.base.aggron.BaseScriptLoader
import com.theoxao.base.common.NatuConfig

/**
 * @author theoxao
 * @date 2019/5/28
 */
class Script(
        var scriptSource: ScriptSource,
        var content: String,
        var extension: String,
        var loader: BaseScriptLoader,
        var config: NatuConfig?
) {

    override fun toString(): String {
        return this.extension + "(" + this.scriptSource.url.toString() + ")"
    }
}