//package autodiff.solver.unconstrained
//
//import autodiff.*
//import org.ejml.simple.SimpleMatrix
//import org.ejml.data.DMatrixRMaj
//
//class BFGS(): UnconstrainedSolver() {
//    private val learningRate = 0.0001
//    private val convergenceTolerance = 1E-4
//
//    override fun solve(initialGuess: SimpleMatrix): SimpleMatrix {
//        val lol = DMatrixRMaj()
//        var currentIteration = initialGuess
//        var currentInverseHessian = SimpleMatrix.identity(initialGuess.numRows())
//        var currentGradient = cost.solveGradient(currentIteration)
//        val lineSearch = BacktrackingLineSearch(cost)
//
//        while (true) {
//            if (currentGradient.normF() < convergenceTolerance) return currentIteration
//
//            // get s_k vector
//            val direction = currentGradient.scale(learningRate)
//            val alpha = lineSearch.solveApproximateMinimum(currentIteration, direction)
//            val stepVector = (currentInverseHessian * currentGradient) * -alpha
//            currentIteration += stepVector
//
//            // get y_k vector
//            val newGradient = cost.solveGradient(currentIteration)
//            val gradientDifference = newGradient - currentGradient
//            currentGradient = newGradient
//
//            // update Hessian
//
//            // TODO let's fix this later
////            currentInverseHessian -= (currentInverseHessian * gradientDifference * gradientDifference.transpose() * currentInverseHessian) / (gradientDifference.transpose() * currentInverseHessian * gradientDifference) - (stepVector * stepVector.transpose()) / (gradientDifference.transpose() * stepVector)
//
//        }
//    }
//}
//
//operator fun SimpleMatrix.times(other: SimpleMatrix): SimpleMatrix {
//    return this.mult(other)
//}
//
//operator fun SimpleMatrix.times(scalar: Double): SimpleMatrix {
//    return this.scale(scalar)
//}