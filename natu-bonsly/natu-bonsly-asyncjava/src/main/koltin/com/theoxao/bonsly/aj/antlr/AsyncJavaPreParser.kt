package com.theoxao.bonsly.aj.antlr

import com.theoxao.bonsly.aj.PreParser
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.BailErrorStrategy
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.springframework.stereotype.Component


/**
 * create by theoxao on 2019/7/9
 */
@Component
class AsyncJavaPreParser : PreParser {

    override fun preParse(text: String): String {
        val stream = ANTLRInputStream(text)
        val lexer = JavaLexer(stream)
        val tokenStream = CommonTokenStream(lexer)
        val javaParser = JavaParser(tokenStream)
        javaParser.removeErrorListeners()
        javaParser.errorHandler = BailErrorStrategy()
        val compilationUnit = javaParser.compilationUnit()
        val listener = AsyncGroovyListener(tokenStream)
        ParseTreeWalker.DEFAULT.walk(listener, compilationUnit)
        return listener.whatDidYouHear()
    }
}