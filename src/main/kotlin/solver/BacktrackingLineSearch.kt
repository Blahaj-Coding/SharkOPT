package autodiff.solver

import autodiff.*
import autodiff.operator.*
import solver.GradientDescent

class BacktrackingLineSearch(private val function: Expression) {
    fun solveApproximateMinimum(initialGuess: VariableMap, direction: VariableMap): Double {
        val m: Double = function.solveJacobian(initialGuess).dot(direction) // directional derivative
        var alpha = 1.0 // constant on [0,1], always positive
        val beta = 0.1 // constant on [0,1], always positive
        val tau = 0.9 // constant on [0,1], always positive
        val f_0 = function.evaluate(initialGuess) // f_k
        while (function.evaluate(initialGuess.plus(direction.times(alpha))) - f_0 > alpha * beta * m) {
            alpha *= tau
        }
        return alpha
    }
}