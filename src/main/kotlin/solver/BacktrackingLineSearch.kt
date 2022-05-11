package autodiff.solver

import autodiff.*
import autodiff.operator.*
import solver.GradientDescent

class BacktrackingLineSearch(private val function: Expression) {
    val gradientDescent = GradientDescent(function)
    fun tryToWork(initialGuess: VariableMap, convergenceTolerance: Double): VariableMap {
        val alpha = solveApproximateMinimum(initialGuess, function.solveJacobian(initialGuess).times(-1.0))
        return gradientDescent.solveMinimum(initialGuess, alpha, convergenceTolerance)
    }

    fun solveApproximateMinimum(initialGuess: VariableMap, direction: VariableMap): Double {
        val m: Double = function.solveJacobian(initialGuess).dot(direction.times(1/direction.norm())) // directional derivative
        println("directional derivative: $m")
        var alpha = 1.0 // constant on [0,1], always positive
        val beta = 1.0 // constant on [0,1], always positive
        val tau = 0.8 // constant on [0,1], always positive

        val f_0 = function.evaluate(initialGuess) // f_k
        var f_k = function.evaluate(initialGuess.plus(direction.times(alpha))) // f_(k + 1)

        // weird logic but will exit when funcAtGuess - funcDelta >= alpha * t
        while (f_k - f_0 > alpha * beta * m) {
            alpha *= tau
            f_k = function.evaluate(initialGuess.plus(direction.times(alpha))) // recalc f_(k + 1)
            println("x_k: ${initialGuess.plus(direction.times(alpha))} f_k: ${f_k}")
        }

        return alpha
    }
}

/** Test */
fun main() {
    val x = Variable("x")
    val exp = Power(x, Constant(2.0))
    val initGuess = VariableMap()
    initGuess.put(x, -1.0)
    val direction = VariableMap()
    direction.put(x, 3.0)
    val backstab = BacktrackingLineSearch(exp)
    val alpha = backstab.solveApproximateMinimum(initGuess, direction)
    println("Alpha: $alpha")
    println("Solution ${initGuess.plus(direction.times(alpha))}")
}