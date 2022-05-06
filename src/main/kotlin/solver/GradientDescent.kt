package solver

import autodiff.*
import autodiff.operator.*

class GradientDescent(private val function: Expression) {
    private val learningRate = -0.4

    fun solveMinimum(initialGuess: HashMap<Variable, Double>, rate: Double = learningRate ): HashMap<Variable, Double> {
        var currentIteration = HashMap(initialGuess)
        for (i in 1..200) {
            var jacobian = function.solveJacobian(currentIteration)
//            println(jacobian, " ", currentIteration)
            println(currentIteration)
            for (key in currentIteration.keys) {
                if (jacobian.get(key)!! != 0.0) {
                    currentIteration.put(key, currentIteration.get(key)!! + rate * jacobian.get(key)!!)
                }
            }
        }
//        var jacobian = function.evaluate()
        return currentIteration
    }
}

fun main() {
    var x = Variable("x")
    var solver = GradientDescent(Power(x, Constant(4.0)))
    var map = HashMap<Variable, Double>()
    map.put(x, 1.0)
    println(solver.solveMinimum(map))
}