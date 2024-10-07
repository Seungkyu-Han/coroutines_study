package seungkyu.coroutinecontext.context

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import seungkyu.coroutinecontext.util.kLogger


private val log = kLogger()
fun main() {
    runBlocking(CoroutineName("runBlocking")) {
        log.info("name in runBlocking: {}",
            this.coroutineContext[CoroutineName])

        withContext(CoroutineName("withContext")) {
            log.info("name in withContext: {}",
                this.coroutineContext[CoroutineName])
        }
    }
}