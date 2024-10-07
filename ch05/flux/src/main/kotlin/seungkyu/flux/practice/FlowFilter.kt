package seungkyu.flux.practice

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import seungkyu.flux.util.kLogger

private val log = kLogger()

fun main(){
    runBlocking {
        val numbers = flowOf(1, 2, 3, 4, 5)

        numbers.filter{
            it % 2 == 0
        }.collect{
            log.info("value1: {}", it)
        }

        numbers.filterNot{
            it % 2 == 0
        }.collect{
            log.info("value2: {}", it)
        }

        val objects = flowOf(10, "20", emptyList<String>(), null)

        objects.filterIsInstance<Int>().collect{
            log.info("value3: {}", it)
        }

        objects.filterNotNull().collect{
            log.info("value4: {}", it)
        }
    }
}