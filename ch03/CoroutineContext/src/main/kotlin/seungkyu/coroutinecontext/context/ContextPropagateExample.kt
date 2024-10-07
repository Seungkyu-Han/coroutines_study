package seungkyu.coroutinecontext.context

import kotlinx.coroutines.runBlocking
import seungkyu.coroutinecontext.util.kLogger
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private val log = kLogger()

private suspend fun child(){
    log.info("context in suspend: {}", coroutineContext)

    var result = suspendCoroutine<Int> {continuation ->
        log.info("context by continuation: {}", continuation.context)
        continuation.resume(100)
    }
    log.info("result: {}", result)
}

fun main(args: Array<String>){
    runBlocking {
        log.info("context in CoroutineScope: {}", this.coroutineContext)
        child()
    }
}