package com.theoxao

import com.theoxao.base.aggron.BaseScriptLoader
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

/**
 * @author theoxao
 * @date 2019/5/28
 */
@SpringBootApplication(scanBasePackages = ["com.theoxao"])
open class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}