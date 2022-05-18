package autodiff

import autodiff.operator.*
import autodiff.solver.constrained.PenaltyMethod
import autodiff.solver.unconstrained.AltaLineSearch
import autodiff.solver.unconstrained.GradientDescent
import kotlin.system.measureNanoTime

fun main(args: Array<String>) {
    var n = 500
    var xs = ArrayList<Variable>()
    var varMap = VariableMap()
    for (k in 0..n) {
        xs.add(Variable("x$k"))
        varMap[xs[k]] = 1.0
    }
    var cost: Expression = Constant(0.0)
    for (i in 0..n) {
        cost += xs[i] * xs[i] * i.toDouble() - xs[n]
    }
    cost *= 0.5

    var solver = AltaLineSearch(cost)
    var time = measureNanoTime {
        solver.solveApproximateMinimum(varMap)
    }
    println(time/1E9)

//    var time = measureNanoTime {
//        var solver = GradientDescent(cost)
//        solver.solveMinimum(varMap)
//    }
//    println(time/1E9)
}