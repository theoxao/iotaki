package com.theoxao.lileep.amqp

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.core.env.PropertiesPropertySource
import java.util.*

/**
 * @author theo
 * @date 19-8-20
 */
@SpringBootApplication
open class Application {

    @Bean
    open fun runner(rabbitTemplate: RabbitTemplate, context: ApplicationContext): CommandLineRunner {
        return CommandLineRunner {

        }
    }


    private fun addListener(queue: String, context: ApplicationContext) {
        val child = AnnotationConfigApplicationContext()
        child.parent = context
        val environment = child.environment
        val properties = Properties()
        properties.setProperty("queue.name", queue)
        val pps = PropertiesPropertySource("props", properties)
        environment.propertySources.addLast(pps)

    }

}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}