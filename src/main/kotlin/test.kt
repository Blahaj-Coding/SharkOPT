package autodiff

import autodiff.operator.*
import autodiff.solver.constrained.PenaltyMethod
import autodiff.solver.unconstrained.AltaLineSearch
import autodiff.solver.unconstrained.GradientDescent
import kotlin.system.measureNanoTime

fun main(args: Array<String>) {
    val solver = AltaLineSearch()
    var n = 1000
    var xs = ArrayList<Variable>()
    for (k in 0..n) {
        xs.add(solver.variable())
        solver.setInitialValue(xs[k], 1.0)
    }
    var cost: Expression = Constant(0.0)
    for (i in 0..n) {
        cost += xs[i] * xs[i] * i.toDouble() - xs[n]
    }
    cost *= 0.5

    var time = measureNanoTime {
        solver.solve()
    }
    println(time/1E9)

//    var time = measureNanoTime {
//        var solver = GradientDescent(cost)
//        solver.solveMinimum(varMap)
//    }
//    println(time/1E9)
}