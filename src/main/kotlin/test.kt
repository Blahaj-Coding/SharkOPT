package autodiff

import autodiff.*
import autodiff.operator.*
import solver.*

var defVal = 5.0

fun main(args: Array<String>) {
//    var x = Variable("x")
//    var solver = GradientDescent(Power(x, Constant(4.0)))
//    var map = VariableMap()
//    map.put(x, 1.0)
//    println(solver.solveMinimum(map))
    test(6.0)
}

fun test(test: Double = defVal) {
    println(test)
}