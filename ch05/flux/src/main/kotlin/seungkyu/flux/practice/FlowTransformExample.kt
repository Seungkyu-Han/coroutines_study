package seungkyu.flux.practice

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking
import seungkyu.flux.util.kLogger

private val log = kLogger()

fun main(){
    runBlocking {
        val numbers = flowOf(1, 2, 3, 4, 5)

        numbers.transform { number -> emit(number * number)}
            .collect { log.info("value1: {}", it) }

        numbers.transform{
            item -> if (item % 2 == 0){
                emit(item * item)
                emit(item * item * item)
            }
        }.collect{
            log.info("value2: {}", it)
        }
    }
}