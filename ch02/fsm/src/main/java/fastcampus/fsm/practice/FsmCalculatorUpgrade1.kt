package fastcampus.fsm.practice

import fastcampus.fsm.util.kLogger
import jogamp.graph.geom.plane.AffineTransform.multiply
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.resume

private val log = kLogger()

class FsmCalculatorUpgrade1 {

    data class Shared(
        var result: Int = 0,
        var label: Int = 0
    )

    fun calculate(initValue: Int, shared: Shared? = null){
        var current = shared ?: Shared()

        val continuation = object: Continuation<Int> {
            override val context: CoroutineContext get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<Int>) {
                current.result = result.getOrThrow()
                this@FsmCalculatorUpgrade1.calculate(initValue, shared)
            }
        }

        when(current.label){
            0 -> {
                current.label = 1
                initialize(initValue, continuation)
            }
            1 -> {
                val initialized = current.result as Int
                current.label = 2
                addOne(initialized, continuation)
            }
            2 -> {
                val added = current.result as Int
                current.label = 3
                multiplyTwo(added, continuation)
            }
            3 -> {
                val multiplied = current.result as Int
                log.info("Result: {}", multiplied)
                return
            }
        }
    }

}

private fun initialize(value: Int, cont: Continuation<Int>){
    log.info("INITIAL")
    cont.resume(value)
}

private fun addOne(value: Int, cont: Continuation<Int>){
    log.info("Add one")
    cont.resume(value + 1)
}

private fun multiplyTwo(value: Int, cont: Continuation<Int>){
    log.info("Multiply two")
    cont.resume(value * 2)
}


fun main() {
    FsmCalculatorUpgrade1().calculate(5)
}