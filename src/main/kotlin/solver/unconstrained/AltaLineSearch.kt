package autodiff.solver.unconstrained

import autodiff.*
import autodiff.operator.*
import org.ejml.simple.SimpleMatrix
import kotlin.math.abs

class AltaLineSearch(): UnconstrainedSolver() {
    private var alpha = Variable()

    /**
     * Optimizes the step size "alpha" for gradient descent to minimize the function f(x, y, z, ...)
     * by iterating with Newton's method using the derivatives df/d(alpha) & d^2f/d(alpha)^2
      */
    override fun solve(initial: SimpleMatrix): SimpleMatrix {
        val gradient = cost.solveGradient(initial)
        // Initializing alpha
        alpha.index = 0
        var currentIteration = SimpleMatrix(1,1)
        currentIteration[0] = 0.0 // Alpha is initialized to zero
        // Replace variables with function of alpha
        val alphaMap = mutableMapOf<Variable, Expression>()
        for (variable in cost.getVariables()) {
             // Substitute the linear relationship for alpha into each variable x = x(alpha) + x'(alpha) * alpha)
            alphaMap[variable] = alpha * gradient[variable.index] + initial[variable.index]
        }
        val alphaFunction = replaceVariables(cost, alphaMap)
        // Fixed number of Newton iterations - a better method of estimating convergence should be used
        for (k in 1..100) {
            var coef = alphaFunction.solveDerivatives(alpha, currentIteration, 2) // derivatives of f with respect to alpha
//            println(coef)
            var delta = abs(coef[1] / coef[2])
//            println(delta)
            currentIteration[0] -= delta
//            println(currentIteration[0])
        }
        return initial + gradient.scale(currentIteration[0])
    }

    private fun replaceVariables(exp: Expression, map: MutableMap<Variable, Expression>): Expression = when(exp) {
        is Variable -> map[exp]!!
        is Sum -> Sum(replaceVariables(exp.left, map), replaceVariables(exp.right, map))
        is Difference -> Difference(replaceVariables(exp.left, map), replaceVariables(exp.right, map))
        is Product -> Product(replaceVariables(exp.left, map), replaceVariables(exp.right, map))
        is Quotient -> Quotient(replaceVariables(exp.numerator, map), replaceVariables(exp.denominator, map))
        is Sin -> Sin(replaceVariables(exp.expression, map))
        is Cos -> Cos(replaceVariables(exp.expression, map))
        is Log -> Log(replaceVariables(exp.base, map), replaceVariables(exp.expression, map))
        is Power -> Power(replaceVariables(exp.base, map), exp.exponent)
        is Max -> Max(replaceVariables(exp.a, map), replaceVariables(exp.b, map))
        is Min -> Min(replaceVariables(exp.a, map), replaceVariables(exp.b, map))
        else -> exp
    }
}