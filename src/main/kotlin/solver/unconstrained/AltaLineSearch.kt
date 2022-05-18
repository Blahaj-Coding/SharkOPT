package autodiff.solver.unconstrained

import autodiff.*
import autodiff.operator.*

class AltaLineSearch(private val function: Expression) {
    /**
     * Optimizes the step size "alpha" for gradient descent to minimize the function f(x, y, z, ...)
     * by iterating with Newton's method using the derivatives df/dalpha & d^2f/dalpha^2
      */
    fun solveApproximateMinimum(initialGuess: VariableMap): VariableMap {
        var iteration = initialGuess
        var alpha = Variable("alpha")
        // Arbitrary number of iterations - the terminating condition should depend on some sort of convergence estimate
        for (k in 1..200) {
            val gradient = function.solveGradient(iteration)
            var alphaMap = HashMap<Variable, Expression>()
            for (variable in gradient.map.keys) {
                 // Substitute the linear relationship for alpha into each variable x = x(alpha) + x'(alpha) * alpha)
                alphaMap[variable] = alpha * gradient.get(variable) + iteration.get(variable)
            }
            val alphaFunction = replaceVariables(function, alphaMap)
            var varMap = VariableMap()
            varMap.put(alpha, 0.0) // alpha is initialized to 0
            // Fixed number of Newton iterations - a better method of estimating convergence should be used
            for (k in 1..2) {
                var coef = alphaFunction.solveDerivatives(alpha, varMap, 2) // derivatives of f with respect to alpha
                var newAlpha = varMap.get(alpha) - coef.get(1) / coef.get(2)
                varMap.put(alpha, newAlpha)
            }
            iteration += gradient * varMap.get(alpha)
        }
        return iteration
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
        else -> exp
    }
}