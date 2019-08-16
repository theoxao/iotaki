package com.theoxao

import org.springframework.stereotype.Component

/**
 * @author theoxao
 * @date 2019/5/28
 */
@Component
class BarBean {
    private val beanName = "bar"

    fun whatsMyName(): String {
        return "you name is $beanName"
    }
}