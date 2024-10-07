package seungkyu.coroutine.scope

import kotlinx.coroutines.*
import seungkyu.coroutine.util.kLogger

private val log = kLogger()

@OptIn(ExperimentalCoroutinesApi::class)
fun main(){
    val cs = CoroutineScope(Dispatchers.Default)

    log.info("job1: {}", cs.coroutineContext[Job])
    log.info("parent1: {}", cs.coroutineContext[Job]?.parent)

    cs.launch {
        log.info("job2: {}", cs.coroutineContext[Job])
        log.info("parent2: {}", cs.coroutineContext[Job]?.parent)

        launch {
            log.info("job3: {}", cs.coroutineContext[Job])
            log.info("parent3: {}", cs.coroutineContext[Job]?.parent)
        }

        launch {
            log.info("job4: {}", cs.coroutineContext[Job])
            log.info("parent4: {}", cs.coroutineContext[Job]?.parent)
        }
    }
}