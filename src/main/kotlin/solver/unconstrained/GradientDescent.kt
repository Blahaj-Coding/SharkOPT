package autodiff.solver.unconstrained

import autodiff.*
import org.ejml.simple.SimpleMatrix

class GradientDescent: UnconstrainedSolver() {
    private val lineSearch = AltaLineSearch()
    private val learningRate = -0.01
    private val convergenceTolerance = 1E-4

    /**
     * Runs the function until the solution has converged
     */
    override fun solve(initialGuess: SimpleMatrix): SimpleMatrix {
        var currentIteration = initialGuess
        lineSearch.minimize(cost)
//        val lineSearch = BacktrackingLineSearch(cost)
        while (true) {
            val gradient = cost.solveGradient(currentIteration)
            println(currentIteration)
            currentIteration = lineSearch.solve(currentIteration)
            if (cost.solveGradient(currentIteration).normF() < convergenceTolerance) return currentIteration
        }
    }
}