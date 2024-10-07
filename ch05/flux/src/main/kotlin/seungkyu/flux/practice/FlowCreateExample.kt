package seungkyu.flux.practice

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.runBlocking
import reactor.core.publisher.Flux
import seungkyu.flux.util.kLogger

private val log = kLogger()

fun main(){
    runBlocking {
        val numbers = flowOf(1, 2, 3)
        numbers.collect {
            log.info("value: $it")
        }

        val numbers2 = listOf(1, 2, 3).asFlow()
        numbers2.collect {
            log.info("value2: {}", it)
        }

        val numbers3 = (1 .. 3).asFlow()
        numbers3.collect {
            log.info("value3: {}", it)
        }

        val numbers4 = Flux.just(1, 2, 3).asFlow()
        numbers4.collect {
            log.info("value4: {}", it)
        }
    }
}