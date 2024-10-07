package seungkyu.flux.practice

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import seungkyu.flux.util.kLogger

private val log = kLogger()

fun main(){

    runBlocking {
        val numbers = flowOf(1, 2, 3, 4, 5)

        numbers.map{
            it + it
        }.collect{
            log.info("value: {}", it)
        }

    }
}