package seungkyu.coroutine.scope

import kotlinx.coroutines.CoroutineScope
import seungkyu.coroutine.util.kLogger
import kotlin.coroutines.EmptyCoroutineContext

private val log = kLogger()

fun main(){
    val cs = CoroutineScope(EmptyCoroutineContext)

    log.info("context: {}", cs.coroutineContext)
    log.info("class name: {}", cs.javaClass.simpleName)
}