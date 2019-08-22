package com.theoxao

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

/**
 * @author theo
 * @date 19-8-20
 */
@SpringBootApplication
open class Application {

    @Bean
    open fun runner(rabbitTemplate: RabbitTemplate): CommandLineRunner {
        return CommandLineRunner {
            rabbitTemplate.convertAndSend("script/groovy/nul.groovy", " hello there ")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}