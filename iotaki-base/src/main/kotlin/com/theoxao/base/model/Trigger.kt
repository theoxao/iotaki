package com.theoxao.base.model

import com.theoxao.base.lileep.TriggerHandler

/**
 * @author theoxao
 * @date 2019/5/28
 */
class Trigger {

    lateinit var type: String

    lateinit var handler: Class<in TriggerHandler>

}