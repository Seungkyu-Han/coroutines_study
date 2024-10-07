package seungkyu.coroutinecontext.context

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asContextElement
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import seungkyu.coroutinecontext.util.kLogger

private val log = kLogger()

fun main(){
    runBlocking {
        val greeting = ThreadLocal<String>()
        greeting.set("Hello")

        launch(Dispatchers.IO) {
            log.info("greeting1: {}", greeting.get())
        }

        val aContext = Dispatchers.IO + greeting.asContextElement()

        launch(aContext){
            log.info("aContext1: {}", aContext)
        }

        val bContext = Dispatchers.Default + greeting.asContextElement("hihi")

        launch(bContext){
            log.info("bContext1: {}", bContext)

            launch {
                log.info("bContext2: {}", greeting.get())
            }
        }
    }
}