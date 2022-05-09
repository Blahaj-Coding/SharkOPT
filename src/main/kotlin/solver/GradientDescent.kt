package solver

import autodiff.*
import autodiff.operator.*

class GradientDescent(private val function: Expression) {
    private val learningRate = -0.4

    fun solveMinimum(initialGuess: VariableMap, rate: Double = learningRate ): VariableMap {
        var currentIteration = initialGuess.copy()
        for (i in 1..200) {
            var jacobian = function.solveJacobian(currentIteration)
            currentIteration = currentIteration.plus(jacobian.times(learningRate))
        }
        return currentIteration
    }
}