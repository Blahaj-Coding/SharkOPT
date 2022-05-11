package autodiff

import autodiff.*
import autodiff.operator.*
import autodiff.solver.*

fun main(args: Array<String>) {
    val x = Variable("x")
    val exp = Power(x, Constant(2.0))
    val initGuess = VariableMap()
    initGuess.put(x, -1.0)
    val direction = VariableMap()
    direction.put(x, 3.0)
    val backstab = BacktrackingLineSearch(exp)
    val alpha = backstab.solveApproximateMinimum(initGuess, direction)
    println("Alpha: $alpha")
    println("Solution ${initGuess.plus(direction.times(alpha))}")
}