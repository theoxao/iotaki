package com.theoxao

import com.theoxao.base.aggron.ScriptLoader
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

/**
 * @author theoxao
 * @date 2019/5/28
 */
@SpringBootApplication
open class Application {

    @Bean
    open fun runner(scriptLoader: ScriptLoader): CommandLineRunner {
        return CommandLineRunner {
            scriptLoader.load()
        }
    }

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}