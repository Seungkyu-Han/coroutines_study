package seungkyu.coroutine.scope

import kotlinx.coroutines.*
import seungkyu.coroutine.util.kLogger

private val log = kLogger()

suspend fun main(){
    val cs = CoroutineScope(Dispatchers.Default)
    val csJob = cs.coroutineContext[Job]

    val job = cs.launch {
        launch {
            withTimeout(500){
                try {
                    delay(1000)
                    log.info("job2: I'm done")
                }catch (e: Exception){
                    log.info("job2: I'm cancelled")
                    log.info("e2: {}", e.message)
                }
            }
        }

        launch {
            try{
                delay(1000)
                log.info("job3: I'm done")
            }catch (e: Exception){
                log.info("job3: I'm cancelled")
                log.info("e3: {}", e.message)
            }
        }
    }

    job.join()

    log.info("job is cancelled: {}", job.isCancelled)
    log.info("csJob is cancelled: {}", csJob?.isCancelled)

}