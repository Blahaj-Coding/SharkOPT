package solver

import autodiff.*

@Suppress("DuplicatedCode")
class GradientDescent(private val function: Expression) {
    private val defaultLearningRate = -0.4
    private val defaultConvergenceTolerance = 1E-4

    /**
     * Runs the function until the solution has converged
     */
    fun solveMinimum(initialGuess: VariableMap,
                     learningRate: Double = defaultLearningRate,
                     convergenceTolerance: Double = defaultConvergenceTolerance): VariableMap {
        var currentIteration = initialGuess.copy()
        while (true) {
            val jacobian = function.solveJacobian(currentIteration)
            currentIteration = currentIteration.plus(jacobian.times(learningRate))
            if (jacobian.norm() < convergenceTolerance) return currentIteration
        }
    }

    /**
     * Solves for the minimum of the function given a certain number of iterations
     * to complete the procedure in with the condition to satisfy convergence if
     * a convergence VariableMap is passed into the function
     */
    fun solveMinimumByIterations(initialGuess: VariableMap,
                                 iterations: Int,
                                 learningRate: Double = defaultLearningRate,
                                 convergenceTolerance: Double = defaultConvergenceTolerance) : VariableMap {
        var currentIteration = initialGuess.copy()
        for (i in 1..iterations) {
            val jacobian = function.solveJacobian(currentIteration)
            currentIteration = currentIteration.plus(jacobian.times(learningRate))
            if (jacobian.norm() < convergenceTolerance) {
                println("iterations: $i")
                break
            }
        }
        return currentIteration
    }
}