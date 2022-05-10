package solver

import autodiff.*

@Suppress("DuplicatedCode")
class GradientDescent(private val function: Expression) {
    private val defaultLearningRate = -0.4
    private val defaultIterations = 200
    private val defaultConvergenceValue = 0.001
    private val defaultConvergenceMap = VariableMap()

    init {
        function.getVariables().forEach { defaultConvergenceMap.put(it, defaultConvergenceValue) }
    }

    /**
     * Runs the function until the solution has converged
     */
    fun solveMinimum(initialGuess: VariableMap, learningRate: Double = defaultLearningRate,
                     convergenceMap: VariableMap = defaultConvergenceMap): VariableMap {
        var currentIteration = initialGuess.copy()
        while (true) {
            val jacobian = function.solveJacobian(currentIteration)
            currentIteration = currentIteration.plus(jacobian.times(learningRate))
            if (jacobian.doesJacobianConverge(convergenceMap)) return currentIteration
        }
    }

    /**
     * Solves for the minimum of the function given a certain number of iterations
     * to complete the procedure in with the condition to satisfy convergence if
     * a convergence VariableMap is passed into the function
     */
    fun solveMinimumByIterations(initialGuess: VariableMap, learningRate: Double = defaultLearningRate,
                                 iterations: Int = defaultIterations,
                                 convergenceMap: VariableMap = VariableMap()) : VariableMap {
        var currentIteration = initialGuess.copy()
        for (i in 1..iterations) {
            val jacobian = function.solveJacobian(currentIteration)
            currentIteration = currentIteration.plus(jacobian.times(learningRate))
            if (convergenceMap.map.isNotEmpty() && jacobian.doesJacobianConverge(convergenceMap)) {
                println("iterations: $i")
                return currentIteration
            }
        }
        return currentIteration
    }
}

//private fun VariableMap.doesJacobianConverge(convergenceValue: Double) : Boolean {
//    for (partial in this.map.values) {
//        if (kotlin.math.abs(partial) > convergenceValue) return false
//    }
//    return true
//}

private fun VariableMap.doesJacobianConverge(convergenceMap: VariableMap) : Boolean {
    for (variable in convergenceMap.map.keys) {
        if (kotlin.math.abs(this.map.get(variable)!!) > convergenceMap.map.get(variable)!!) return false
    }
    return true
}