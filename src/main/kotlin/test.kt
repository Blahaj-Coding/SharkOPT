package autodiff

import autodiff.*
import autodiff.operator.*
import solver.*

fun main(args: Array<String>) {
    var x = Variable("x")
    var solver = GradientDescent(Power(x, Constant(4.0)))
    var map = VariableMap()
    map.put(x, 1.0)
    println(solver.solveMinimum(map))
}