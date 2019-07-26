package com.theoxao.base.model

import com.theoxao.base.lileep.BaseTriggerHandler

/**
 * @author theoxao
 * @date 2019/5/28
 */
class Trigger {

    lateinit var type: String

    lateinit var handler: Class<in BaseTriggerHandler>

}