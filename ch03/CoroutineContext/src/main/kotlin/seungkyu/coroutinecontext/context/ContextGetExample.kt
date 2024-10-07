package seungkyu.coroutinecontext.context

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import seungkyu.coroutinecontext.util.kLogger

private val log = kLogger()

@OptIn(ExperimentalStdlibApi::class)
fun main(){
    val context = CoroutineName("custom name") + Dispatchers.IO

    val element1 = context[CoroutineName]
    log.info("element1: $element1")

    val element2 = context[CoroutineDispatcher]
    log.info("element2: $element2")

    val element3 = context[Job]
    log.info("element3: $element3")
}