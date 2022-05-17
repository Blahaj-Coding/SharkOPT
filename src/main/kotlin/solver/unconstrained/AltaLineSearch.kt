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
        // Arbitrary number of iterations - the terminating condition should depend on some sort of convergence estimate
        for (k in 1..200) {
            var alpha = Variable("alpha")
            val gradient = function.solveGradient(iteration)
            // println("gradient ${gradient.norm()}")
            for (item in gradient.map) {
                var variable = item.key
                /**
                 * Substitute the linear relationship for alpha into each variable x = x(alpha) + x'(alpha) * alpha)
                 * I really hate modifying the variable class itself for this, a better solution would be nice
                  */
                variable.setEqual(Constant(iteration.get(variable)) + Constant(gradient.get(variable)) * alpha)
            }
            var varMap = VariableMap()
            varMap.put(alpha, 0.0) // alpha is initialized to 0
            // Fixed number of Newton iterations - a better method of estimating convergence should be used
            for (k in 1..5) {
                // Calculates the derivative of f with respect to alpha
                var coef = function.solveDerivatives(alpha, varMap, 2)
                var newAlpha = varMap.get(alpha) - coef.get(1) / coef.get(2)
                // println(coef)
                varMap.put(alpha, newAlpha)
            }
            iteration = iteration.plus(gradient.times(varMap.get(alpha)))
        }
        return iteration
    }
}