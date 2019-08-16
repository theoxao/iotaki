package com.theoxao.base.model

import java.net.URI
import java.net.URL

/**
 * @author theoxao
 * @date 2019/5/28
 */
class ScriptSource(var url: URI, var content: String) {

    companion object {
        fun bkdrHash(str: String): Int {
            val seed = 131
            var hash = 0
            str.toCharArray().forEach {
                hash = (hash * seed) + it.toInt()
            }
            return hash.and(0x7FFFFFFF)
        }
    }

    var hash: Int = 0

    init {
        hash = content.hashCode()
    }


}