package com.theoxao

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

    @Before("execution(* org.springframework.shell.Shell.evaluate(..))")
    fun before() {
        println("i am in")
    }

}