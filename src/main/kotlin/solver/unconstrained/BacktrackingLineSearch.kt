package autodiff.solver.unconstrained

import autodiff.*
import org.ejml.simple.SimpleMatrix

// Implements equation 3.4 on page 33, chapter 3 http://www.apmath.spbu.ru/cnsa/pdf/monograf/Numerical_Optimization2006.pdf
class BacktrackingLineSearch(private val function: Expression) {
    fun solveApproximateMinimum(initialGuess: SimpleMatrix, direction: SimpleMatrix): Double {
        val m: Double = function.solveGradient(initialGuess).dot(direction) // directional derivative
        var alpha = 1.0 // positive non-zero constant
        val beta = 0.1 // constant on [0,1], always positive
        val tau = 0.9 // constant on [0,1], always positive
        val f_0 = function.evaluate(initialGuess) // f_k
        while (function.evaluate(initialGuess + direction.scale(alpha)) - f_0 > alpha * beta * m) {
            alpha *= tau
        }
        return alpha
    }
}