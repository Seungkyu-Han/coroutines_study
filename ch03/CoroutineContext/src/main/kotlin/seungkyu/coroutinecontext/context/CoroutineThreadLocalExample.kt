package seungkyu.coroutinecontext.context

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import seungkyu.coroutinecontext.util.kLogger

private val log = kLogger()

fun main(){
    val greeting = ThreadLocal<String>()
    greeting.set("hello")

    runBlocking {
        log.info("thread: {}", Thread.currentThread().name)
        log.info("greeting: {}", greeting.get())

        launch(Dispatchers.IO) {
            log.info("thread: {}", Thread.currentThread().name)
            log.info("thread: {}", greeting.get())
        }
    }
}