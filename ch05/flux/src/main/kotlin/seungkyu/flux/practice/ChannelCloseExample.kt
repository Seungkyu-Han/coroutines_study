package seungkyu.flux.practice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import seungkyu.flux.util.kLogger

private val log = kLogger()

fun main(){
    runBlocking {

        val channel = Channel<Int>(Channel.Factory.CONFLATED)
        launch(Dispatchers.IO){
            log.info("sending")
            channel.send(1)
            channel.send(2)
            channel.send(3)
        }

        for (i in 1..3){
            log.info("value: {}", channel.receive())
        }
    }

}