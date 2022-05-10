package autodiff.solver

import autodiff.*
import autodiff.operator.*
import autodiff.VariableMap
import solver.GradientDescent

class BacktrackingLineSearch(private val function: Expression) {
    val gradientDescent = GradientDescent(function)
    fun tryToWork(initialGuess: VariableMap, convergenceTolerance: Double): VariableMap {
        val alpha = solveApproximateMinimum(initialGuess, function.solveJacobian(initialGuess).times(-1.0))
        return gradientDescent.solveMinimum(initialGuess, alpha, convergenceTolerance)
    }

    fun solveApproximateMinimum(initialGuess: VariableMap, direction: VariableMap): Double {
        val c: Double = 0.5 // constant on [0,1], always positive
        val tau: Double = 0.8 // constant on [0,1], always positive
        val m: Double = function.solveJacobian(initialGuess).dot(direction.times(1/direction.norm())) // directional derivative
        println("m: $m")
//        val t: Double = m * c // was -m * c
        var alpha = 1.0 // constant on [0,1], always positive
        println("a0: $alpha")

        val funcAtGuess = function.evaluate(initialGuess) // f_k
        println("initial evaluation $funcAtGuess")
        var funcDelta = function.evaluate(initialGuess.plus(direction.times(alpha))) // f_(k + 1)
        println("x: ${initialGuess}")
        println("x: ${initialGuess.plus(direction.times(alpha))}")
        var lowest: Double = funcAtGuess
        // weird logic but will exit when funcAtGuess - funcDelta >= alpha * t
        while (funcDelta - funcAtGuess > m * c * direction.times(alpha).norm()) {
            alpha *= tau
            println("x: ${initialGuess.plus(direction.times(alpha))}")
            funcDelta = function.evaluate(initialGuess.plus(direction.times(alpha))) // recalc f_(k + 1)
            if (kotlin.math.abs(funcDelta) < kotlin.math.abs(lowest)) lowest = funcDelta // log
        }

        println("lowest slope: $lowest")
        return alpha
    }
}

typealias BackstabbingLineSearch = BacktrackingLineSearch


/** Test */
fun main() {
    val x = Variable("x")
    val exp = Power(x, Constant(4.0))
    val initGuess = VariableMap()
    initGuess.put(x, 1.0)
    val direction = VariableMap()
    direction.put(x, -2.0)
    val backstab = BackstabbingLineSearch(exp)
    val alpha = backstab.solveApproximateMinimum(initGuess, direction)
    println("Alpha: $alpha")
    println("Solution ${initGuess.plus(direction.times(alpha))}")
}