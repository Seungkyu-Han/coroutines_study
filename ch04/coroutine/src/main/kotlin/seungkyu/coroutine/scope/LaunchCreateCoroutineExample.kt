package seungkyu.coroutine.scope

import kotlinx.coroutines.*
import seungkyu.coroutine.util.kLogger
import kotlin.coroutines.EmptyCoroutineContext

private val log = kLogger()

fun main(){
    runBlocking {
        val cs = CoroutineScope(EmptyCoroutineContext)
        log.info("job: {}", cs.coroutineContext[Job])

        val job = cs.launch {
            delay(100)

            log.info("context: {}", this.coroutineContext)
            log.info("class name: {}", this.javaClass.simpleName)
            log.info("parent: {}", this.coroutineContext[Job]?.parent)
        }

        log.info("step1")
        job.join()
        log.info("step2")
    }
}