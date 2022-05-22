package autodiff.solver.unconstrained

import autodiff.*
import org.ejml.simple.SimpleMatrix

class GradientDescent: UnconstrainedSolver() {
    private val lineSearch = BacktrackingLineSearch()
    private val learningRate = -0.1
    private val convergenceTolerance = 1E-4

    /**
     * Runs the function until the solution has converged
     */
    override fun solve(initialGuess: SimpleMatrix): SimpleMatrix {
        var currentIteration = initialGuess
        lineSearch.minimize(cost)
//        val lineSearch = BacktrackingLineSearch(cost)
        while (true) {
//            println(currentIteration)
            var nextIteration = lineSearch.solve(currentIteration)
            if ((nextIteration - currentIteration).normF() < convergenceTolerance) return nextIteration
            currentIteration = nextIteration
        }
    }
}