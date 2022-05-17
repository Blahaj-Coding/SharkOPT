package autodiff.solver.unconstrained

import autodiff.*
import autodiff.solver.unconstrained.BacktrackingLineSearch

@Suppress("DuplicatedCode")
class GradientDescent(private val function: Expression) {
    private val defaultLearningRate = -0.4
    private val defaultConvergenceTolerance = 1E-6
    private val lineSearch: BacktrackingLineSearch = BacktrackingLineSearch(function)

    /**
     * Runs the function until the solution has converged
     */
    fun solveMinimum(initialGuess: VariableMap,
                     learningRate: Double = defaultLearningRate,
                     convergenceTolerance: Double = defaultConvergenceTolerance): VariableMap {
        var currentIteration = initialGuess.copy()
        while (true) {
            val gradient = function.solveGradient(currentIteration)
            val direction = gradient.times(learningRate)
            val alpha = lineSearch.solveApproximateMinimum(currentIteration, direction)
            currentIteration = currentIteration.plus(direction.times(alpha))
            if (gradient.norm() < convergenceTolerance) return currentIteration
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
            val gradient = function.solveGradient(currentIteration)
            currentIteration = currentIteration.plus(gradient.times(learningRate))
            if (gradient.norm() < convergenceTolerance) {
                println("iterations: $i")
                break
            }
        }
        return currentIteration
    }
}