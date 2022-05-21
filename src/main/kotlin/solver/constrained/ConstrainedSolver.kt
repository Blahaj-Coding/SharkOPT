package autodiff.solver.constrained

import autodiff.*
import autodiff.autodiff.Equation
import org.ejml.simple.SimpleMatrix

abstract class ConstrainedSolver {
    protected var cost: Expression = Constant(0.0)
    protected var constraints = mutableListOf<Equation>()

    abstract fun solve(initialGuess: SimpleMatrix): SimpleMatrix

    /**
     * Solve the problem using the initial values specified in the Variable objects
     */
    fun solve(): SimpleMatrix {
        var variables = cost.getVariables()
        for (constraint in constraints) {
            variables += constraint.expression.getVariables()
        }
        println(variables)
        val rows = variables.size
        val initialGuess = SimpleMatrix(rows, 1)
        println(initialGuess)
        for ((index, variable) in variables.withIndex()) {
            variable.index = index
            initialGuess[index] = variable.getInitial()
        }
        return solve(initialGuess)
    }

    /**
     * Adds a constraint to the solver
     */
    fun subjectTo(constraint: Equation) {
        constraints.add(constraint)
    }

    fun minimize(cost: Expression) {
        this.cost = cost
    }
}