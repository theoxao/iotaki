package com.theoxao

import com.theoxao.base.aggron.BaseScriptLoader
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.shell.SpringShellAutoConfiguration

/**
 * @author theoxao
 * @date 2019/5/28
 */
@SpringBootApplication(scanBasePackages = ["com.theoxao"])
@Import(FutureResultHandlerConfiguration::class)
open class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}