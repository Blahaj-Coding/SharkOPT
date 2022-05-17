package autodiff

import autodiff.operator.*
import autodiff.solver.constrained.PenaltyMethod
import autodiff.solver.unconstrained.GradientDescent

fun main(args: Array<String>) {
    var x = Variable("x")
    var z = Variable("z")
//    var exp = (Constant(3.0) * x) / (Sin(Constant(5.0) * x * x))
    var exp = (Log(x, Power(Constant(5.0) * x * x + z, Constant(3.0))))
    var values = VariableMap()
    values.put(x, 5.0)
    values.put(z, 3.0)
    println(exp.forwardAutoDiff(x, values, 3))

    // should be max squared of 0 and x^2 - 4 jk changing it to test
    val exp1 = Power(Max(Constant(0.0), Power(x, Constant(2.0)) - Constant(4.0)), Constant(2.0))

    val map1 = VariableMap()
    map1.put(x, 5.0)

    println(exp1.forwardAutoDiff(x, map1, 3))

}