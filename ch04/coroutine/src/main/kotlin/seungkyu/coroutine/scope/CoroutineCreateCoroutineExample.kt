package seungkyu.coroutine.scope

import kotlinx.coroutines.*
import seungkyu.coroutine.util.kLogger
import kotlin.coroutines.EmptyCoroutineContext

private val log = kLogger()

@OptIn(ExperimentalCoroutinesApi::class)
fun main(){
    runBlocking {
        val cs = CoroutineScope(EmptyCoroutineContext)

        val job = cs.launch {
            delay(100)
            log.info("job: {}", this.coroutineContext[Job])

            val job2 = this.launch {
                delay(500)

                log.info("parent job: {}", this.coroutineContext[Job]?.parent)
                log.info("coroutine2 finished")
            }
            job2.join()
            log.info("coroutine1 finished")
        }

        log.info("step1")
        job.join()
        log.info("step2")
    }
}