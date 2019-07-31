package com.theoxao.bonsly.groovy.annotations

import org.codehaus.groovy.transform.GroovyASTTransformationClass


/**
 * @author theo
 * @date 2019/6/26
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.LOCAL_VARIABLE, AnnotationTarget.FIELD)
@GroovyASTTransformationClass("com.theoxao.bonsly.groovy.ast.AutowiredASTTransform")
annotation class Autowired
