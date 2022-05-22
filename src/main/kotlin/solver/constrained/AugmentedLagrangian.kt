package autodiff.solver.constrained

import autodiff.Constant
import autodiff.Expression
import autodiff.autodiff.Equation
import autodiff.operator.Max
import autodiff.operator.Power
import autodiff.solver.unconstrained.GradientDescent
import org.ejml.simple.SimpleMatrix

class AugmentedLagrangian: ConstrainedSolver() {
    override fun solve(initialGuess: SimpleMatrix): SimpleMatrix {
        val solver = GradientDescent()
        var currentIteration = initialGuess
        var mu = 10.0
        //  Penalty method terms
        var penaltyTerms: Expression = Constant(0.0)
        var lambda = SimpleMatrix(equalityConstraints.size, 1)
        for ((k, constraint) in equalityConstraints.withIndex()) {
            penaltyTerms += constraint * constraint
            lambda[k] = 0.0
        }

        // Arbitrary number of iterations... replace with something better later
        for (k in 1..10) {
            // Augmented lagrangian terms
            var lagrangian: Expression = Constant(0.0)
            for ((k, constraint) in equalityConstraints.withIndex()) {
                lagrangian += constraint * lambda[k]
            }
            // Solve penalty problem
            solver.minimize(cost + penaltyTerms * mu / 2 + lagrangian)
            currentIteration = solver.solve(currentIteration)
            // Update lambda
            for ((k, constraint) in equalityConstraints.withIndex()) {
                lambda[k] += mu * constraint.evaluate(currentIteration)
            }
            mu *= 10
            println(currentIteration)
        }
        return currentIteration
    }
}