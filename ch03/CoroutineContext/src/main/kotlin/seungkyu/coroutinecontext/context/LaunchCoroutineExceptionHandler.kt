package seungkyu.coroutinecontext.context

import kotlinx.coroutines.*
import seungkyu.coroutinecontext.util.kLogger

private val log = kLogger()

fun main(){
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            log.error("exception caught")
        }

        val job = CoroutineScope(Dispatchers.IO + handler).launch{
            launch {
                launch {
                    throw Exception()
                }
            }
        }

        job.join()
    }
}