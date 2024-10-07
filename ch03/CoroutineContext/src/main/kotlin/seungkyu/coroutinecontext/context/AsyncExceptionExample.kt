package seungkyu.coroutinecontext.context

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import seungkyu.coroutinecontext.util.kLogger

private val log = kLogger()

fun main(){
    runBlocking{
        val deferred = CoroutineScope(Dispatchers.IO).async {
            throw Exception()
            10
        }

        try{
            deferred.await()
        }catch (e: Exception){
            log.info("exception!!!")
        }
    }
}