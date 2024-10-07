package seungkyu.coroutinecontext.context

import kotlinx.coroutines.*
import seungkyu.coroutinecontext.util.kLogger

private val log = kLogger()

@OptIn(ExperimentalStdlibApi::class)
private fun CoroutineScope.defaultDispatcher(): CoroutineDispatcher? {
    return this.coroutineContext[CoroutineDispatcher.Key]
}

fun main(){
    runBlocking {
        log.info("thread: {}", Thread.currentThread().name)
        log.info("dispatcher: {}", this.defaultDispatcher())

        withContext(Dispatchers.Default) {
            log.info("thread: {}", Thread.currentThread().name)
            log.info("dispatcher: {}", this.defaultDispatcher())
        }

        withContext(Dispatchers.IO) {
            log.info("thread: {}", Thread.currentThread().name)
            log.info("dispatcher: {}", this.defaultDispatcher())
        }

        CoroutineScope(CoroutineName("cs")).launch {
            log.info("thread: {}", Thread.currentThread().name)
            log.info("dispatcher: {}", this.defaultDispatcher())
        }
    }
}