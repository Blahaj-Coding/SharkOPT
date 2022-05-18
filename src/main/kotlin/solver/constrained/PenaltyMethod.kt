package autodiff.solver.constrained

import autodiff.*
import autodiff.operator.Power
import autodiff.solver.unconstrained.GradientDescent
import autodiff.solver.unconstrained.NLPSolver

class PenaltyMethod: NLPSolver() {
    var constraints = ArrayList<Expression>()

    override fun solve(): MutableMap<Variable, Double> {
        TODO("Not yet implemented")
    }

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

    fun solve(penalty: Double): MutableMap<Variable, Double> {
//        var constraintCost: Expression = Constant(0.0)
//        for (constraint in constraints) {
//            constraintCost = constraintCost + constraint
//        }
//        val solver = GradientDescent(cost + Constant(penalty) * constraintCost)
//        return solver.solveMinimum(initialGuess)
        TODO("Not yet implemented")
    }


    enum class Inequality {
        GreaterThan, LessThan
    }
}