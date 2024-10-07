package seungkyu.coroutine.scope

import kotlinx.coroutines.*
import seungkyu.coroutine.util.kLogger
import kotlin.coroutines.EmptyCoroutineContext

private val log = kLogger()

@OptIn(ExperimentalCoroutinesApi::class)
fun main(){
    runBlocking {
        val cs = CoroutineScope(EmptyCoroutineContext)
        log.info("job: {}", cs.coroutineContext.job)

        val deferred = cs.async{
            delay(100)

            log.info("context: {}", this.coroutineContext)
            log.info("class name: {}", this.javaClass.simpleName)
            log.info("parent: {}", this.coroutineContext.job.parent)

            100
        }

        log.info("step1")
        log.info("result: {}", deferred.await())
        log.info("step2")
    }
}