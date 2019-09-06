package com.theoxao.aggron

import com.theoxao.aggron.config.FileRootConfiguration
import com.theoxao.base.aggron.ScriptLoader
import com.theoxao.base.common.IotakiConfig
import com.theoxao.base.model.ScriptModel
import com.theoxao.base.model.ScriptSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.ResourceUtils
import org.yaml.snakeyaml.Yaml
import java.io.File
import javax.annotation.Resource

/**
 * @author theoxao
 * @date 2019/5/28
 */
@Component
class PathFileScriptLoader : ScriptLoader() {

    override suspend fun notifyChanged() = TODO()

    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java.name)
    }

    override fun refreshAll() = TODO()

    @Resource
    lateinit var fileRootConfiguration: FileRootConfiguration

    init {
        basePath = ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX).file!!
    }


    override suspend fun load(): List<ScriptModel> {
        val root = File(basePath + fileRootConfiguration.rootPath)
        assert(root.isDirectory) { "root path should be a directory instead of file" }
        val files = root.flatFiles(this, null)
        log.info("load {} files from {}", files.size, fileRootConfiguration.rootPath)
        return files
    }

    private fun File.flatFiles(loader: ScriptLoader, parentConfig: IotakiConfig?): List<ScriptModel> {
        val files = this.listFiles()
        val iotaki = files?.find { it.isFile && it.name == iotakiFileName }?.readText()
        //if config does not exist , use parent config
        val iotakiConfig = iotaki?.let { Yaml().loadAs(iotaki, IotakiConfig::class.java) } ?: parentConfig
        return files?.filter {
            (iotakiConfig?.ignore?.contains(it.name) ?: false || it.name == iotakiFileName).not()
        }?.flatMap {
            val list = arrayListOf<ScriptModel>()
            if (it.isDirectory)
                list.addAll(it.flatFiles(loader, iotakiConfig))
            else {
                val content = it.readText()
                list.add(ScriptModel(
                        ScriptSource(it.toURI(), content),
                        content,
                        it.extension,
                        loader,
                        iotakiConfig,
                        iotakiConfig?.bean?.contains(it.name) ?: false))
            }
            return@flatMap list
        } ?: arrayListOf()
    }
}
