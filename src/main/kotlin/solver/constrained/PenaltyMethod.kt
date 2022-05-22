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
        var mu = 10.0
        var penaltyTerms: Expression = Constant(0.0)
        for (constraint in equalityConstraints) {
            penaltyTerms += Power(constraint, 2)
        }
        println((penaltyTerms).solveGradient(currentIteration))
        println(penaltyTerms)
        // Arbitrary number of iterations... replace with something better later
        for (k in 1..20) {
            solver.minimize(cost + penaltyTerms * mu)
            currentIteration = solver.solve(currentIteration)
            mu *= 10
//            println(currentIteration)
        }
        println((penaltyTerms).solveGradient(currentIteration))
        return currentIteration
    }
}