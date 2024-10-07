package seungkyu.coroutinecontext.context

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import seungkyu.coroutinecontext.util.kLogger
import kotlin.coroutines.EmptyCoroutineContext

private val log = kLogger()

fun main(){
    val context1 = EmptyCoroutineContext
    log.info("context: {}", context1)

    val element1 = CoroutineName("Custom name1")
    val context2 = context1 + element1
    log.info("context2: {}, class: {}", context2, context2.javaClass.name)

    val element2 = CoroutineName("Custom name2")
    val context3 = context2 + element2
    log.info("context3: {}, class: {}", context3, context3.javaClass.name)

    val element3 = Dispatchers.IO
    val context4 = context3 + element3
    log.info("context4: {}, class: {}", context4, context4.javaClass.name)

    val element4 = Job()
    val context5 = context4 + element4
    log.info("context5: {}, class: {}", context5, context5.javaClass.name)
}