package autodiff.solver.unconstrained

import autodiff.*

import org.ejml.simple.SimpleMatrix

abstract class UnconstrainedSolver {
    var cost: Expression = Constant(0.0)

    abstract fun solve(initialGuess: SimpleMatrix): SimpleMatrix

    fun solve(): SimpleMatrix {
        val rows = cost.getVariables().size
        val initialGuess = SimpleMatrix(rows, 1)
        for ((index, variable) in cost.getVariables().withIndex()) {
            variable.index = index
            initialGuess[index] = variable.getInitial()
        }
        return solve(initialGuess)
    }

    fun minimize(cost: Expression) {
        this.cost = cost
    }
}