package com.theoxao.aggron

import com.theoxao.aggron.config.FileRootConfiguration
import com.theoxao.base.aggron.ScriptLoader
import com.theoxao.base.model.Script
import com.theoxao.base.model.ScriptSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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

    companion object {
        const val ignoreFileName = ".ignore"
        val log: Logger = LoggerFactory.getLogger(this::class.java.name)
    }

    override fun refreshAll() = TODO()

    @Resource
    lateinit var fileRootConfiguration: FileRootConfiguration

    override fun load(): List<Script> {
        val root = File(ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).file + fileRootConfiguration.rootPath)
        assert(root.isDirectory) { "root path should be a directory instead of file" }
        val files = root.flatFiles()
        log.info("load {} files from {}", files.size, fileRootConfiguration.rootPath)
        return files.map {
            val content = it.readText()
            Script(ScriptSource(it.toURI(), content), content, it.extension, this)
        }
    }


    private fun File.flatFiles(): List<File> {
        val files = this.listFiles()
        val ignores = files?.find { it.isFile && it.name == ignoreFileName }?.readLines()
        return files?.filter { file ->
            !(ignores?.map { it == file.name }?.reduce(Boolean::or) ?: false || file.name == ignoreFileName)
        }?.flatMap {
            val list = arrayListOf<File>()
            if (it.isDirectory)
                list.addAll(it.flatFiles())
            else
                list.add(it)
            return@flatMap list
        } ?: arrayListOf()
    }
}