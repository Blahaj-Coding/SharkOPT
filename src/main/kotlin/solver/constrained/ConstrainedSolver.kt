package autodiff.solver.constrained

import autodiff.*
import autodiff.autodiff.Equation
import org.ejml.simple.SimpleMatrix

abstract class ConstrainedSolver {
    protected var cost: Expression = Constant(0.0)
    // List of expressions which are constrained to equal zero
    protected var equalityConstraints = mutableListOf<Expression>()

    abstract fun solve(initialGuess: SimpleMatrix): SimpleMatrix

    /**
     * Solve the problem using the initial values specified in the Variable objects
     */
    fun solve(): SimpleMatrix {
        var variables = cost.getVariables()
        for (constraint in equalityConstraints) {
            variables += constraint.getVariables()
        }
        val rows = variables.size
        val initialGuess = SimpleMatrix(rows, 1)
        for ((index, variable) in variables.withIndex()) {
            variable.index = index
            initialGuess[index] = variable.getInitial()
        }
        // println(initialGuess)
        return solve(initialGuess)
    }

    /**
     * Adds a constraint to the solver
     */
    fun subjectTo(constraint: Equation) {
        when (constraint.operator) {
            Equation.Operator.EQUAL_TO_ZERO -> equalityConstraints.add(constraint.expression)
            Equation.Operator.LESS_THAN_ZERO -> {
                var slack = Variable()
                equalityConstraints.add(constraint.expression + slack * slack)
            }
            Equation.Operator.LESS_THAN_OR_EQUAL_TO_ZERO -> {
                var slack = Variable()
                equalityConstraints.add(constraint.expression + slack * slack)
            }
        }
    }

    fun minimize(cost: Expression) {
        this.cost = cost
    }
}