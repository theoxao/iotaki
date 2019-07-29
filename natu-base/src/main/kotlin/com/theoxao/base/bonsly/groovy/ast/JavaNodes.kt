package com.theoxao.base.bonsly.groovy.ast

import org.codehaus.groovy.ast.ClassHelper.make
import org.codehaus.groovy.ast.ClassNode

object JavaNodes {
    val stringNode = make(String::class.java)!!
    val metaApiNode = make(MetaApi::class.java)!!
    val listNode = make(List::class.java)!!
    val anyNode = make(Any::class.java)!!
    val exceptionNode = make(Exception::class.java)!!

    val tsNode: ClassNode? = make(Class.forName("org.springframework.transaction.TransactionStatus"))
    val tmNode: ClassNode? = make(Class.forName("org.springframework.transaction.PlatformTransactionManager"))
    val transactionDefinitionNode: ClassNode? = make(Class.forName("org.springframework.transaction.support.DefaultTransactionDefinition"))
}
