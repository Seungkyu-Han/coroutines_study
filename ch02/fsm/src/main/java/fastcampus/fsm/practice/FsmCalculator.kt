package fastcampus.fsm.practice

import fastcampus.fsm.util.kLogger

private val log = kLogger()

object FsmCalculator {
    data class Shared(
        var result: Int = 0,
        var label: Int = 0
    )

    fun calculate(initialValue: Int, shared: Shared? = null){
        val current = shared ?: Shared()

        when(current.label){
            0 -> {
                current.result = initialValue
                current.label = 1
            }
            1 -> {
                current.result += 1
                current.label = 2
            }
            2 -> {
                current.result *= 2
                current.label = 3
            }
            3 -> {
                log.info("Result: {}", current.result)
                return
            }
        }

        this.calculate(initialValue, current)
    }
}

fun main(){
    FsmCalculator.calculate(5)
}