package com.theoxao

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

/**
 * @author theoxao
 * @date 2019/5/28
 */
@SpringBootApplication(scanBasePackages = ["com.theoxao"])
open class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
    Thread.sleep(10000)
}