package com.theoxao.bonsly.groovy

import com.theoxao.bonsly.groovy.ast.ParameterNameTransform.Companion.PARAMETER_NAMES_FIELD_SUFFIX
import groovy.lang.Script
import org.springframework.core.ParameterNameDiscoverer
import java.lang.reflect.Constructor
import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap


/**
 * @author theo
 * @date 2019/6/20
 */
class ScriptParamNameDiscoverer(val script: Script) : ParameterNameDiscoverer {

    companion object {
        val cache = object : ConcurrentHashMap<String, Array<String>>() {
            override fun put(key: String, value: Array<String>): Array<String>? {
                if (size > 500) {
                    val subList = keys().toList().subList(0, 200)
                    subList.forEach {
                        remove(it)
                    }
                }
                return super.put(key, value)
            }
        }
    }


    override fun getParameterNames(method: Method): Array<String>? {
        var pns = cache[method.signature()]
        if (pns == null) {
            val property = script.getProperty("${method.name}$PARAMETER_NAMES_FIELD_SUFFIX")
            pns = (property as ArrayList<String>).toTypedArray()
            cache[method.signature()] = pns
        }
        return pns
    }

    override fun getParameterNames(p0: Constructor<*>): Array<String>? = TODO()


    fun Method.signature() = this.declaringClass.name +
            "#${this.name}" +
            "(${if (this.parameterTypes.isNotEmpty()) this.parameterTypes.map { it.name }.reduce { acc, s -> "$acc,$s" } else ""})"
}
