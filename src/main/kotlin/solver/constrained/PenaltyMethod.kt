package autodiff.solver.constrained

import autodiff.Constant
import autodiff.Expression
import autodiff.VariableMap
import autodiff.operator.Power
import autodiff.solver.unconstrained.GradientDescent

class PenaltyMethod {
    var initialGuess = VariableMap()
    var constraints = ArrayList<Expression>()
    var cost: Expression = Constant(0.0)

    fun addEqualityConstraint(LHS: Expression, RHS: Expression) {
        constraints.add(Power(LHS - RHS, Constant(2.0)))
    }

    fun addInequalityConstraint(LHS: Expression, inequalityType: Inequality, RHS: Expression) {
        TODO("finish when Min and Max Operators are completed")
//        when (inequalityType) {
//            Inequality.GreaterThan -> Power()
//            Inequality.LessThan -> Power()
//        }
    }

    fun minimize(cost: Expression) {
        this.cost = cost
    }

    fun solve(penalty: Double): VariableMap {
        var constraintCost: Expression = Constant(0.0)
        for (constraint in constraints) {
            constraintCost = constraintCost + constraint
        }
        val solver = GradientDescent(cost + Constant(penalty) * constraintCost)
        return solver.solveMinimum(initialGuess)
    }


    enum class Inequality {
        GreaterThan, LessThan
    }
}