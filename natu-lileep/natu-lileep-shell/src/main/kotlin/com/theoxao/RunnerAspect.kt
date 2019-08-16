package com.theoxao

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

/**
 * @author theo
 * @date 19-8-13
 */
@Aspect
@Component
class RunnerAspect {

    @Before(value = "execution(public * org.springframework.shell.Shell.*(..))")
    fun before(joinPoint: JoinPoint) {
        println(joinPoint.signature.toLongString())
    }

}