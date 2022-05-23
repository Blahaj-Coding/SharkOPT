package autodiff.solver.unconstrained

import autodiff.*
import org.ejml.simple.SimpleMatrix
import org.ejml.data.DMatrixRMaj

class BFGS(): UnconstrainedSolver() {
    private val convergenceTolerance = 1E-4
    private val lineSearch = AltaLineSearch()

    override fun solve(initialGuess: SimpleMatrix): SimpleMatrix {
        val lol = DMatrixRMaj()
        var currentIteration = initialGuess
        var currentGradient = cost.solveGradient(currentIteration)
        var currentInverseHessian = SimpleMatrix.identity(initialGuess.numRows()) * currentGradient.normF()
        lineSearch.minimize(cost)

        while (true) {
            if (currentGradient.normF() < convergenceTolerance) return currentIteration

            // get s_k vector
            val alphaGradient = currentIteration - lineSearch.solve(currentIteration) // should return -alpha * grad f
            val stepVector = currentInverseHessian * alphaGradient
            currentIteration += stepVector

            // get y_k vector
            val newGradient = cost.solveGradient(currentIteration)
            val gradientDifference = newGradient - currentGradient
            currentGradient = newGradient

            // update Hessian

            // TODO let's fix this later
            val denominatorOne = (gradientDifference.transpose() * currentInverseHessian * gradientDifference).convertToDouble()
            currentInverseHessian -= (currentInverseHessian * gradientDifference * gradientDifference.transpose()
                * currentInverseHessian) / denominatorOne
            val denominatorTwo = (gradientDifference.transpose() * stepVector).convertToDouble()
            currentInverseHessian += (stepVector * stepVector.transpose()) / denominatorTwo
        }
    }
}

operator fun SimpleMatrix.times(other: SimpleMatrix): SimpleMatrix {
    return this.mult(other)
}

operator fun SimpleMatrix.times(scalar: Double): SimpleMatrix {
    return this.scale(scalar)
}

operator fun SimpleMatrix.div(scalar: Double): SimpleMatrix {
    return this.scale(1 / scalar)
}

fun SimpleMatrix.convertToDouble(): Double {
    // probably a better way to say this, brain empty moment
    if (this.numRows() == 1 && this.numCols() == 1) return this.elementSum()
    else throw Exception("Not a matrix of size 1x1")
}