package autodiff.solver.constrained

import autodiff.*
import autodiff.autodiff.Equation
import autodiff.operator.*
import autodiff.operator.Power
import autodiff.solver.unconstrained.GradientDescent
import org.ejml.simple.SimpleMatrix

class PenaltyMethod: ConstrainedSolver() {
    override fun solve(initialGuess: SimpleMatrix): SimpleMatrix {
        val solver = GradientDescent()
        var currentIteration = initialGuess
        var penaltyScalar = 100.0
        var penaltyTerms: Expression = Constant(0.0)
        for (constraint in constraints) {
            penaltyTerms += penaltyFunction(constraint)
        }
        // Arbitrary number of iterations... replace with something better later
        for (k in 1..10) {
            solver.minimize(cost + penaltyTerms * penaltyScalar)
            currentIteration = solver.solve(currentIteration)
            penaltyScalar *= 10
        }
        return currentIteration
    }

    private fun penaltyFunction(equation: Equation): Expression {
        return when (equation.operator) {
            Equation.Operator.EQUAL_TO_ZERO -> Power(equation.expression, 2)
            Equation.Operator.LESS_THAN_OR_EQUAL_TO_ZERO -> Power(Max(0, equation.expression), 2)
            Equation.Operator.LESS_THAN_ZERO -> Power(Max(0, equation.expression), 2)
        }
    }
}