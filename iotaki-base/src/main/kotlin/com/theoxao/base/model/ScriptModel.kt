package com.theoxao.base.model

import com.theoxao.base.aggron.ScriptLoader
import com.theoxao.base.common.IotakiConfig

/**
 * @author theoxao
 * @date 2019/5/28
 */
class ScriptModel(
        var scriptSource: ScriptSource,
        var content: String,
        var extension: String,
        var loader: ScriptLoader,
        var config: IotakiConfig?,
        var shouldBean: Boolean = false
) {

    override fun toString(): String {
        return this.extension + "(" + this.scriptSource.url.toString() + ")"
    }
}
