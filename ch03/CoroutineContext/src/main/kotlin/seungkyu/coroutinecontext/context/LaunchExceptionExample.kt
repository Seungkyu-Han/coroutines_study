package seungkyu.coroutinecontext.context

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import seungkyu.coroutinecontext.util.kLogger

private val log = kLogger()

fun main(){
    runBlocking {
        val job = CoroutineScope(Dispatchers.IO).launch {
            launch {
                launch {
                    throw Exception()
                }
            }
        }

        try{
            job.join()
        }catch (e: Exception){
            log.error("error!!!")
        }
    }
}