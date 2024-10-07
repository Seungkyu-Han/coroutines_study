package seungkyu.flux.practice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import seungkyu.flux.util.kLogger

private val log = kLogger()

fun main(){
    runBlocking {
        val channel = Channel<Int>()

        launch (Dispatchers.IO) {
            channel.send(1)
            log.info("sent 1")

            channel.send(2)
            log.info("sent 2")

            channel.send(3)
            log.info("sent 3")
        }

        for (i in 1..5){
            delay(100)
            log.info("value: {}", channel.receive())
        }
    }
}