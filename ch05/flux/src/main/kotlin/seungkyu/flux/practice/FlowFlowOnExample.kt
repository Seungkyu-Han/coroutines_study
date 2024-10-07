package seungkyu.flux.practice

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import seungkyu.flux.util.kLogger

private val log = kLogger()

private suspend fun square(x: Int): Int{
    delay(1000)
    log.info("current: {}", x)
    return x*x
}

fun main(args: Array<String>){
    runBlocking {
        val squareFlow = flow{
            emit(square(10))
            emit(square(20))
            emit(square(30))
        }.flowOn(Dispatchers.IO)

        squareFlow.collect {
            log.info("value: {}", it)
        }
    }
}