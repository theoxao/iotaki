package com.theoxao.aggron

import com.theoxao.aggron.config.FileRootConfiguration
import com.theoxao.base.aggron.ScriptLoader
import com.theoxao.base.model.Script
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import java.io.File
import javax.annotation.Resource

/**
 * @author theoxao
 * @date 2019/5/28
 */
@Component
class ClasspathFileScriptLoader : ScriptLoader() {
    override fun refreshAll() = TODO()

    @Resource
    lateinit var fileRootConfiguration: FileRootConfiguration

    override fun load(): List<Script> {
        val root = File(ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).file + fileRootConfiguration.rootPath)
        assert(root.isDirectory) { "root path should be a directory instead of file" }
        return arrayListOf()
    }
}