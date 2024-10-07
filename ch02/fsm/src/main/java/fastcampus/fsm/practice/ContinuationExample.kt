package fastcampus.fsm.practice

import fastcampus.fsm.util.kLogger
import kotlin.coroutines.*

private val log = kLogger()

fun main(){
    var visited = false

    val continuation = object: Continuation<Int>{
        override val context: CoroutineContext
            get() = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            if(visited){
                log.info("Result: $result")
            }
            else{
                log.info("Visit now")
                visited = true
            }
        }
    }

    continuation.resume(10)
    continuation.resume(10)
    continuation.resumeWithException(
        IllegalStateException()
    )
}