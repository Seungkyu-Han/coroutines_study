package seungkyu.coroutinecontext.context

import kotlinx.coroutines.runBlocking
import seungkyu.coroutinecontext.util.kLogger
import kotlin.coroutines.coroutineContext

private val log = kLogger()

suspend fun nested(){
    log.info("Context in nested: {}", coroutineContext)
}

suspend fun outer(){
    log.info("Context in outer: {}", coroutineContext)
    nested()
}

fun main(){
    runBlocking {
        outer()
    }
}