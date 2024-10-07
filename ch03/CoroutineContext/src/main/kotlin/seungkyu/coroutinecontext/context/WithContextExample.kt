package seungkyu.coroutinecontext.context

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import seungkyu.coroutinecontext.util.kLogger

private val log = kLogger()

fun main(){
    runBlocking {
        log.info("context in runBlocking: {}", this.coroutineContext)

        withContext(CoroutineName("withContext")){
            val ctx = this.coroutineContext
            log.info("context in withContext: {}", ctx)
        }

        log.info("context in runBlocking: {}", this.coroutineContext)
    }
    log.info("context in main: {}", coroutineContext)
}