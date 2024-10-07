package seungkyu.coroutine.scope

import kotlinx.coroutines.*
import seungkyu.coroutine.util.kLogger

private val log = kLogger()

suspend fun main(){
    val cs = CoroutineScope(Dispatchers.Default)

    cs.launch {
        launch {
            delay(1000)
            log.info("job2: I'm done")
        }

        launch{
            try{
                delay(1000)
                log.info("job3: I'm done")
            }
            catch(e:Exception){
                log.info("job3: I'm cancelled")
                log.info("e: {}", e.message)
            }
        }

        delay(1000)
        log.info("job1: I'm done")
    }

    delay(1000)

    cs.cancel()
    log.info("finished")
}