package autodiff

import autodiff.operator.*
import autodiff.solver.constrained.PenaltyMethod
import autodiff.solver.unconstrained.AltaLineSearch
import autodiff.solver.unconstrained.GradientDescent
import kotlin.system.measureNanoTime

fun main(args: Array<String>) {
//    var x = Variable("x")
//    var z = Variable("z")
////    var exp = (Constant(3.0) * x) / (Sin(Constant(5.0) * x * x))
//    var exp = Log(x, Power(Constant(5.0) * x * x + z, Constant(3.0)))
//    var values = VariableMap()
//    values.put(x, 5.0)
//    values.put(z, 3.0)
//    println(exp.solveDerivatives(z, values, 3))
//
//    // should be max squared of 0 and x^2 - 4 jk changing it to test
//    val exp1 = Power(Max(Constant(0.0), Power(x, Constant(2.0)) - Constant(4.0)), Constant(2.0))
//
//    val map1 = VariableMap()
//    map1.put(x, 5.0)
//
//    println(exp1.forwardAutoDiff(x, map1, 3))
    var n = 500
    var xs = ArrayList<Variable>()
    var varMap = VariableMap()
    for (k in 0..n) {
        xs.add(Variable("x$k"))
        varMap.put(xs.get(k), 1.0)
    }
    var cost: Expression = Constant(0.0)
    for (i in 0..n) {
        cost = cost + xs.get(i) * xs.get(i) * Constant(i.toDouble()) - xs.get(n)
    }
    cost = cost * Constant(0.5)

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