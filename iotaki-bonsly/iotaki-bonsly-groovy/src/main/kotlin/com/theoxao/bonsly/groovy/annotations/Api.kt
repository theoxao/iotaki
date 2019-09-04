package com.theoxao.bonsly.groovy.annotations

import org.codehaus.groovy.transform.GroovyASTTransformationClass


/**
 * @author theo
 * @date 2019/6/27
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
@GroovyASTTransformationClass("com.theoxao.bonsly.groovy.ast.ApiASTTransform")
annotation class Api(val uri: String, val requestMethod: String = "GET", val method: String = "")
