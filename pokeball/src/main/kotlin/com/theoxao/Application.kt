package com.theoxao

import com.theoxao.lileep.shell.FutureResultHandlerConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

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