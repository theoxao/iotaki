package com.theoxao

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.future.future
import org.springframework.shell.result.TerminalAwareResultHandler
import java.util.concurrent.CompletableFuture

/**
 * @author theo
 * @date 19-8-15
 */
class FutureResultHandler : TerminalAwareResultHandler<CompletableFuture<Any?>>() {
    override fun doHandleResult(result: CompletableFuture<Any?>?) {
        GlobalScope.future {
            terminal.writer().println(result?.await()?.toString())
        }
    }

}