package seungkyu.coroutinecontext

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoroutineContextApplication

fun main(args: Array<String>) {
    runApplication<CoroutineContextApplication>(*args)
}
