package com.theoxao.lileep.amqp

import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry
import org.springframework.stereotype.Component
import javax.annotation.Resource

/**
 * @author theo
 * @date 19-8-20
 */
@Component
class Nothing {

    @Resource
    lateinit var registry: RabbitListenerEndpointRegistry

    fun test() {
    }

}