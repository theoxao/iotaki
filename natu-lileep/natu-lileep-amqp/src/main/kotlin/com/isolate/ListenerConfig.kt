package com.isolate

import com.theoxao.lileep.amqp.cache
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.AmqpHeaders
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.handler.annotation.Header

/**
 * @author theo
 * @date 19-8-20
 */
@Configuration
@EnableRabbit
open class ListenerConfig {

    @RabbitListener(queues = ["\${queue.name}"])
    open fun listen(msg: String, @Header(AmqpHeaders.CONSUMER_QUEUE) queue: String) {
        GlobalScope.launch {
            cache[queue]?.invoke { method, discoverer ->
                val names = discoverer.getParameterNames(method)
                val args = arrayOfNulls<Any?>(names?.size ?: 0)
                if (args.isNotEmpty()) args[0] = msg
                return@invoke args
            }
        }
    }

    @Bean
    open fun queue(@Value("\${queue.name}") queue: String): Queue {
        return Queue(queue)
    }

    @Bean
    open fun rabbitAdmin(rt: RabbitTemplate): RabbitAdmin {
        return RabbitAdmin(rt)
    }

}