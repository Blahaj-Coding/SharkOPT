package autodiff.solver.unconstrained

import autodiff.*

class GradientDescent(): NLPSolver() {
    private val learningRate = -0.0001
    private val convergenceTolerance = 1E-4

    /**
     * Runs the function until the solution has converged
     */
    override fun solve(): MutableMap<Variable, Double> {
        var currentIteration = initialGuess
        val lineSearch = BacktrackingLineSearch(cost)
        while (true) {
            val gradient = cost.solveGradient(currentIteration)
            println(gradient.norm())
            val direction = gradient * learningRate
            val alpha = lineSearch.solveApproximateMinimum(currentIteration, direction)
            currentIteration = currentIteration.plus(direction.times(alpha))
            if (gradient.norm() < convergenceTolerance) return currentIteration
        }
    }
}