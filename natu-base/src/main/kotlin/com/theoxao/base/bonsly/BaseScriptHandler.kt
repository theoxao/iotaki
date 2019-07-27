package com.theoxao.base.bonsly

import com.theoxao.base.model.Script


abstract class BaseScriptHandler {

    lateinit var supportedFileExtension: Array<String>

    lateinit var target: List<Script>

    abstract fun handle(target: List<Script>)
}