package fastcampus.fsm.practice

import fastcampus.fsm.util.kLogger
import kotlin.coroutines.Continuation

private val log = kLogger()

object CallbackExample {
    fun handleButtonClicked(
        callback: () -> Unit,
        continuation: (count: Int) -> Unit
    ){
        var count = 0

        for (i in 0 until 5){
            count++
            callback()
        }

        continuation(count)
    }
}

fun main(){
    CallbackExample.handleButtonClicked(
        callback = {
            log.info("Button clicked")
        },
        continuation = {
            count -> log.info("$count")
        }
    )
}