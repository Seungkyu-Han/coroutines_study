package seungkyu.flux.practice

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import seungkyu.flux.util.kLogger

private val log = kLogger()

private suspend fun square(x: Int): Int {
    delay(10)
    return x * x
}

fun main() {
    runBlocking {
        val squareFlow: Flow<Int> = flow {
            emit(square(10))
            emit(square(20))
            emit(square(30))
        }

        squareFlow.collect(object: FlowCollector<Int> {
            override suspend fun emit(value: Int) {
                log.info("value: {}", value)
            }
        })

        log.info("again")

        squareFlow.collect {
            log.info("value: {}", it)
        }
    }
}