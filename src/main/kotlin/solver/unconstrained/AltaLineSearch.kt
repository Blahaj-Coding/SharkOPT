package autodiff.solver.unconstrained
//
//import autodiff.*
//import autodiff.operator.*
//
//class AltaLineSearch(private val function: Expression) {
//    fun solveApproximateMinimum(initialGuess: VariableMap, direction: VariableMap): Double {
//        val m: Double = function.solveGradient(initialGuess).dot(direction) // directional derivative
//        var alpha = Variable("alpha")
//        val beta = 0.1 // constant on [0,1], always positive
//        val tau = 0.9 // constant on [0,1], always positive
//        val f_0 = function.evaluate(initialGuess) // f_k
//        for (k in 1..100) {
//            var coef = function.forwardAutoDiff(alpha, )
//            alpha -= (coef.get(1)) / (2 * coef.get(2))
//        }
//        return alpha
//    }
//}